package com.fiberhome.dvorak.netty.server;

import com.fiberhome.dvorak.netty.api.server.Server;
import com.fiberhome.dvorak.netty.api.server.exception.ServerException;
import com.fiberhome.dvorak.netty.api.server.exception.ServerStopException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;



public class NettyServer implements Server {
	
	private KryoNettyServerChannelInitializer serverChannelInitializer;
	
	private int backlogSize = 1024;
	
	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	@Override
	public void start(final int port) {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap
			.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, backlogSize)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true);
		
		serverChannelInitializer = new KryoNettyServerChannelInitializer();
		serverBootstrap.childHandler(serverChannelInitializer);
		
		try {
			channel = serverBootstrap.bind(port).sync().channel();
		} catch (final InterruptedException ex) {
			throw new ServerException(Server.SYSTEM_MESSAGE_ID, ex);
		}
	}
	
	@Override
	public void stop() {
		if (null == channel) {
			throw new ServerStopException();
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		bossGroup = null;
		workerGroup = null;
		channel = null;
	}
	

}
