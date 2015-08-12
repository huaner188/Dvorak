package com.fiberhome.dvorak.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import javax.annotation.Resource;

import com.fiberhome.dvorak.netty.codec.KryoDecoder;
import com.fiberhome.dvorak.netty.codec.KryoEncoder;
import com.fiberhome.dvorak.netty.codec.KryoPool;



public class KryoNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	@Resource
	private NettyServerUploadHandler serverDispatchHandler;
	
	@Resource
	private KryoPool kryoSerializationFactory;
	
	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		kryoSerializationFactory = new KryoPool();
		kryoSerializationFactory.init();
		serverDispatchHandler = new NettyServerUploadHandler();
		ch.pipeline().addLast(new KryoEncoder(kryoSerializationFactory));
		ch.pipeline().addLast(new KryoDecoder(kryoSerializationFactory));
		ch.pipeline().addLast(serverDispatchHandler);
		ch.pipeline().addLast(new NettyServerTopoHandler());
		
	}
}
