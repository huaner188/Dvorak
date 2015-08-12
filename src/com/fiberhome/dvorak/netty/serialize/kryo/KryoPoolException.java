package com.fiberhome.dvorak.netty.serialize.kryo;

import com.fiberhome.dvorak.netty.exception.SystemException;

public final class KryoPoolException extends SystemException {
	
	private static final long serialVersionUID = -2992257109597526961L;
	
	public KryoPoolException(final Exception cause) {
		super(cause);
	}
}
