package com.fiberhome.dvorak.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import javax.annotation.Resource;



public class JavaNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	@Resource
	private NettyServerUploadHandler serverDispatchHandler;
	
	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		serverDispatchHandler = new NettyServerUploadHandler();
		ch.pipeline().addLast(new ObjectEncoder());
		ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
		ch.pipeline().addLast(serverDispatchHandler);
	}
}
