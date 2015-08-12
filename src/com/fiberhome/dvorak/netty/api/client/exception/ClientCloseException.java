package com.fiberhome.dvorak.netty.api.client.exception;

import com.fiberhome.dvorak.netty.exception.SystemException;

public final class ClientCloseException extends SystemException {
	
	private static final long serialVersionUID = 9177859528409152687L;
	
	private static final String MESSAGE = "Can't close this client, beacuse the client didn't connect a server.";
	
	public ClientCloseException() {
		super(MESSAGE);
	}
}
