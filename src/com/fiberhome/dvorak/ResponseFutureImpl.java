package com.fiberhome.dvorak;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fiberhome.dvorak.netty.api.exchange.RpcResponseImpl;


public class ResponseFutureImpl  {
    private volatile RpcResponseImpl response;
    private volatile boolean sendRequestOK = true;
    private volatile Throwable cause;
    private final long messageId;
    private final long timeoutMillis;
    private final long beginTimestamp = System.currentTimeMillis();
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

 
    private final SemaphoreReleaseOnlyOnce once;

    private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);


    public void setFuture(Future<Object> future){
        
    }

    public ResponseFutureImpl(long  messageId, long timeoutMillis,
            SemaphoreReleaseOnlyOnce once) {
        this.messageId = messageId;
        this.timeoutMillis = timeoutMillis;
        this.once = once;
    }





    public void release() {
        if (this.once != null) {
            this.once.release();
        }
    }


    public boolean isTimeout() {
        long diff = System.currentTimeMillis() - this.beginTimestamp;
        return diff > this.timeoutMillis;
    }


    public RpcResponseImpl waitResponse(final long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.response;
    }


    public void putResponse(final RpcResponseImpl response) {
        this.response = response;
        this.countDownLatch.countDown();
    }


    public long getBeginTimestamp() {
        return beginTimestamp;
    }


    public boolean isSendRequestOK() {
        return sendRequestOK;
    }


    public void setSendRequestOK(boolean sendRequestOK) {
        this.sendRequestOK = sendRequestOK;
    }


    public long getTimeoutMillis() {
        return timeoutMillis;
    }




    public Throwable getCause() {
        return cause;
    }


    public void setCause(Throwable cause) {
        this.cause = cause;
    }


    public RpcResponseImpl getResponse() {
        return response;
    }


    public void setResponseCommand(RpcResponseImpl response) {
        this.response = response;
    }


    public long getMessageId() {
        return messageId;
    }


    @Override
    public String toString() {
        return "ResponseFuture [response=" + response + ", sendRequestOK=" + sendRequestOK
                + ", cause=" + cause + ", messageId=" + messageId + ", timeoutMillis=" + timeoutMillis
                +   ", beginTimestamp=" + beginTimestamp
                + ", countDownLatch=" + countDownLatch + "]";
    }
}
