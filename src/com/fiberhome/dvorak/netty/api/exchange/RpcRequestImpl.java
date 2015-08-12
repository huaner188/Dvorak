package com.fiberhome.dvorak.netty.api.exchange;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


public final class RpcRequestImpl  implements Serializable {
	
	private static final long serialVersionUID = 2750646443189480771L;
	
	private static AtomicInteger RequestId = new AtomicInteger(0);
	public final long messageId = RequestId.getAndIncrement();

	public final Class<?> apiClass;

	public final String method;

	public final Object[] parameters;
	
	public RpcRequestImpl(final Class<?> apiClass, final String method, final Object... parameters) {
		this.apiClass = apiClass;
		this.method = method;
		this.parameters = parameters;
	}

}
