package com.fiberhome.dvorak.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.fiberhome.dvorak.Info.TopoInfo;
import com.fiberhome.dvorak.Info.UploadInfo;


public class NettyServerUploadHandler extends SimpleChannelInboundHandler<UploadInfo>  {
	
	
	
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		
		System.out.println("exceptionCaught:"+cause.toString());
		//ctx.writeAndFlush(response);
		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UploadInfo request) throws Exception {
		System.out.println("UploadInfo,channelRead0:"+request.toString());
		
		//Object returnValue = execute(request);
		//ctx.writeAndFlush(new RpcResponseImpl(request.messageId, returnValue));
	}



}
