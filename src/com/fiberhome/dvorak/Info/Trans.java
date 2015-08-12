package com.fiberhome.dvorak.Info;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import com.fiberhome.dvorak.netty.client.NettyClient;

public class Trans {
	private static int flowNum = 5;
	private static int[] lowBound = {5,5,10,20};
	private static int[] step = {5,5,10,20};
	private static InetSocketAddress address = new InetSocketAddress("",8020);
	private static NettyClient client=new NettyClient(100);
	public static void startTrans() throws ConnectException {
		client.connect(address);
	   	//发送拓扑信息
	   	client.channel.writeAndFlush(getTopoInfo.theTopo);
	   	
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 5000, 500);//在5秒后执行此任务,每次间隔0.5秒,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
		while(true){//这个是用来停止此任务的,否则就一直循环执行此任务了
		try {
			int ch = System.in.read();
				if(ch-'c'==0){
					timer.cancel();//使用这个方法退出任务
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
public static class MyTask extends TimerTask{
	public void run() {
		UploadInfo oneUpload=new UploadInfo();
		//设置NodeId
		char NodeId = getTopoInfo.theTopo.getNodeId();
		oneUpload.setNodeId(NodeId);
		
		//设置节点状态	
		boolean status = true;
		oneUpload.setStatus(status);
		
		
		//设置flow参数
		int[] flowId=new int[flowNum];
		int[] flowBandwidth=new int[flowNum];
		
		for(int i=0;i<flowNum;i++)
		{
			flowId[i]=i+1;
			flowBandwidth[i]=0;
		}
		
		switch(NodeId){ 
		   case'A':{			  
			  for(int i=0;i<flowNum-1;i++)
			  {
				  flowBandwidth[i]=(int)(Math.random()*lowBound[i]+step[i]);
			  }
//			  oneUpload.setFlowBandwidth(flowBandwidth);
//			  oneUpload.setFlowId(flowId);
			  break;
		    }
	       case'C':{
	    	   flowBandwidth[4]=(int)(Math.random()*5+15);
//	    	   oneUpload.setFlowBandwidth(flowBandwidth);
//	    	   oneUpload.setFlowId(flowId);
			   break;
		    }
		}
		//System.out.println("NodeId :"+oneUpload.getNodeId());
		client.channel.writeAndFlush(oneUpload); 
//		flowBandwidth=oneUpload.getFlowBandwidth();
//		flowId=oneUpload.getFlowId();
//		  for(int i=0;i<flowNum;i++)
//		  {
//			int j=i+1;
//			System.out.println("flowId"+j+" :"+flowId[i]);
//			System.out.println("flowBandwidth"+j+" :"+flowBandwidth[i]);
//			
//		  }
//		  System.out.println("________________________________________");
	    }
	}
}
