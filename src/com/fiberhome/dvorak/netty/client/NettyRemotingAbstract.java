
package com.fiberhome.dvorak.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.fiberhome.dvorak.ResponseFutureImpl;
import com.fiberhome.dvorak.SemaphoreReleaseOnlyOnce;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingSendRequestException;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingTimeoutException;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingTooMuchRequestException;
import com.fiberhome.dvorak.netty.api.exchange.RpcRequestImpl;
import com.fiberhome.dvorak.netty.api.exchange.RpcResponseImpl;



public abstract class NettyRemotingAbstract {
    


    
    protected final Semaphore semaphoreAsync;


    protected final ConcurrentHashMap<Long , ResponseFutureImpl> responseTable =
            new ConcurrentHashMap<Long, ResponseFutureImpl>();


   
    public NettyRemotingAbstract(final int permitsAsync) {

        this.semaphoreAsync = new Semaphore(permitsAsync, true);
    }


    public void scanResponseTable() {
        Iterator<Entry<Long, ResponseFutureImpl>> it = this.responseTable.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Long, ResponseFutureImpl> next = it.next();
            ResponseFutureImpl rep = next.getValue();

            
        }
    }

    
   

    public RpcResponseImpl invokeSyncImpl(final Channel channel, final RpcRequestImpl request,
            final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
            RemotingTimeoutException {
        try {
            final ResponseFutureImpl responseFuture =
                    new ResponseFutureImpl(request.messageId, timeoutMillis, null);
            this.responseTable.put(request.messageId, responseFuture);
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        responseFuture.setSendRequestOK(true);
                        return;
                    }
                    else {
                        responseFuture.setSendRequestOK(false);
                    }

                    responseTable.remove(request.messageId);
                    responseFuture.setCause(f.cause());
                    responseFuture.putResponse(null);
                    //plog.warn("send a request command to channel <" + channel.remoteAddress() + "> failed.");
                    //plog.warn(request.toString());
                }
            });

            RpcResponseImpl responseCommand = responseFuture.waitResponse(timeoutMillis);
            if (null == responseCommand) {
                if (responseFuture.isSendRequestOK()) {
                    throw new RemotingTimeoutException(channel.toString(),
                        timeoutMillis, responseFuture.getCause());
                }
                else {
                    throw new RemotingSendRequestException(channel.toString(),
                        responseFuture.getCause());
                }
            }

            return responseCommand;
        }
        finally {
            this.responseTable.remove(request.messageId);
        }
    }


  
    abstract public ExecutorService getCallbackExecutor();
    
    public void processResponseCommand(ChannelHandlerContext ctx, RpcResponseImpl cmd) {
        final ResponseFutureImpl responseFuture = responseTable.get(cmd.messageId);
        if (responseFuture != null) {
            responseFuture.setResponseCommand(cmd);
            responseFuture.release();
            responseTable.remove(cmd.messageId);

            
        }
        else {
           // plog.warn("receive response, but not matched any request, "
           //         + RemotingHelper.parseChannelRemoteAddr(ctx.channel()));
           // plog.warn(cmd.toString());
        }

    }


}
