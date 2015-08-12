package com.fiberhome.dvorak.netty.api.exchange;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


public final class RpcResponseImpl  implements Serializable {
	
	private static final long serialVersionUID = 5887232731148682128L;
	

	public final long messageId ;
	
	public final Throwable exception;
	
	public RpcResponseImpl(final long messageId, final Object returnValue) {
		this.messageId = messageId;
		this.appResponse = returnValue;
		this.exception = null;
	}
	
	public RpcResponseImpl(final long messageId, final Throwable exception) {
		this.messageId = messageId;
		this.appResponse = null;
		this.exception = exception;
	}
	public long getMessageId() {
		return this.messageId;
	}


    private String errorMsg;

    private Object appResponse;

    public Object getAppResponse() {
        return appResponse;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean isError(){
        return errorMsg == null ? false:true;
    }
}
