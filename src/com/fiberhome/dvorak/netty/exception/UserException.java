package com.fiberhome.dvorak.netty.exception;


public abstract class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 5975218893665147705L;
	
	private final Object[] args;
	
	protected UserException(final Object... args) {
		this.args = args;
	}
	
	public final String getErrorCode() {
		return getClass().getCanonicalName();
	}
	
	public final String getArguments() {
		return "";
	}
}
