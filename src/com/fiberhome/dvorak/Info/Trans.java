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
	   	//����������Ϣ
	   	client.channel.writeAndFlush(getTopoInfo.theTopo);
	   	
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 5000, 500);//��5���ִ�д�����,ÿ�μ��0.5��,�������һ��Data����,�Ϳ�����ĳ���̶���ʱ��ִ���������.
		while(true){//���������ֹͣ�������,�����һֱѭ��ִ�д�������
		try {
			int ch = System.in.read();
				if(ch-'c'==0){
					timer.cancel();//ʹ����������˳�����
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
		//����NodeId
		char NodeId = getTopoInfo.theTopo.getNodeId();
		oneUpload.setNodeId(NodeId);
		
		//���ýڵ�״̬	
		boolean status = true;
		oneUpload.setStatus(status);
		
		
		//����flow����
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
