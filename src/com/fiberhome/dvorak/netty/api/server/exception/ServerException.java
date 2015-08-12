package com.fiberhome.dvorak.netty.api.server.exception;

import com.fiberhome.dvorak.netty.exception.SystemException;

public final class ServerException extends SystemException {
	
	private static final long serialVersionUID = 5438288073708201395L;
	

	private final long messageId;
	
	public ServerException(final long messageId, final Exception cause) {
		super(cause);
		this.messageId = messageId;
	}

	public long getMessageId() {
		return messageId;
	}
}
