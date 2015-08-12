package com.fiberhome.dvorak.netty.api.client;

import java.net.InetSocketAddress;

import com.fiberhome.dvorak.netty.api.exchange.RpcRequestImpl;
import com.fiberhome.dvorak.netty.api.exchange.RpcResponseImpl;


public interface Client {
	
	void connect(InetSocketAddress socketAddress);
	RpcResponseImpl sent(RpcRequestImpl request);
	InetSocketAddress getRemoteAddress();
	void close();
}
