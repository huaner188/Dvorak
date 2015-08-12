package com.fiberhome.dvorak.netty.api.client.exception;

import com.fiberhome.dvorak.netty.exception.SystemException;

public final class ClientException extends SystemException {
	
	private static final long serialVersionUID = 1400214981125931724L;
	
	public ClientException(final Exception cause) {
		super(cause);
	}
}
