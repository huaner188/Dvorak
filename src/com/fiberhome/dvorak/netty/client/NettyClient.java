package com.fiberhome.dvorak.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import com.fiberhome.dvorak.Info.TopoInfo;
import com.fiberhome.dvorak.netty.api.client.Client;
import com.fiberhome.dvorak.netty.api.client.exception.ClientCloseException;
import com.fiberhome.dvorak.netty.api.client.exception.ClientException;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingConnectException;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingSendRequestException;
import com.fiberhome.dvorak.netty.api.client.exception.RemotingTimeoutException;
import com.fiberhome.dvorak.netty.api.exchange.RpcRequestImpl;
import com.fiberhome.dvorak.netty.api.exchange.RpcResponseImpl;
import com.fiberhome.dvorak.netty.codec.KryoDecoder;
import com.fiberhome.dvorak.netty.codec.KryoEncoder;
import com.fiberhome.dvorak.netty.codec.KryoPool;



public class NettyClient extends NettyRemotingAbstract implements Client  {
	

	
	public NettyClient(int permitsAsync) {
		super(permitsAsync);
		
	}
	private final ExecutorService publicExecutor = null;
	

	
	private EventLoopGroup workerGroup;
	public Channel channel;
	
	@Override
	public void connect(final InetSocketAddress socketAddress) {
		workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	KryoPool kp = new KryoPool();
                	kp.init();
                    ch.pipeline().addLast(//
                    	new KryoEncoder(kp), //
                        new KryoDecoder(kp)
                        );
                }
            });
		
		channel = bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort()).syncUninterruptibly().channel();
	}
	
	

	
	@Override
	public InetSocketAddress getRemoteAddress() {
		SocketAddress remoteAddress = channel.remoteAddress();
		if (!(remoteAddress instanceof InetSocketAddress)) {
			throw new ClientException(new RuntimeException("Get remote address error, should be InetSocketAddress"));
		}
		return (InetSocketAddress) remoteAddress;
	}
	
	@Override
	public void close() {
		if (null == channel) {
			throw new ClientCloseException();
		}
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		workerGroup = null;
		channel = null;
	}
	
	 public RpcResponseImpl invokeSync( final RpcRequestImpl request, long timeoutMillis)
	            throws InterruptedException, RemotingConnectException, RemotingSendRequestException,
	            RemotingTimeoutException {
	        if (channel != null && channel.isActive()) {
	            try {
	                RpcResponseImpl response = this.invokeSyncImpl(channel, request, timeoutMillis);
	                
	                return response;
	            }
	            catch (RemotingSendRequestException e) {
	                
	                throw e;
	            }
	            catch (RemotingTimeoutException e) {
	                
	                throw e;
	            }
	        }
	        else {
	       
	            throw new RemotingConnectException("");
	        }
	    }

	   
		@Override
		public ExecutorService getCallbackExecutor() {
			return this.publicExecutor;
			
		}

		@Override
		public RpcResponseImpl sent(RpcRequestImpl request) {
			return null;
		}
}
