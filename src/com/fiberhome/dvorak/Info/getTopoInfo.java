package com.fiberhome.dvorak.Info;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class getTopoInfo {
	
	static TopoInfo theTopo;
	static{
	    Properties prop = new Properties();
	    theTopo=new TopoInfo();
	    ArrayList<String> portId = new ArrayList<String>();
	    ArrayList<String> pairPortId = new ArrayList<String>();
	    int power=(int)(Math.random()*100);
	  try{
		  //读取属性文件topoInfo.properties
		  InputStream in = new BufferedInputStream (new FileInputStream("topoInfo.properties"));
		  prop.load(in);     ///加载属性列表
		  Iterator<String> it=prop.stringPropertyNames().iterator();
		  while(it.hasNext())
		  {
		   String key=it.next();
		   //System.out.println(key+":"+prop.getProperty(key));
		   //属性类赋值
		   if(key.equals("nodeId"))
		   {
			   theTopo.setNodeId(prop.getProperty(key).charAt(0));
			   System.out.println("nodeId :"+prop.getProperty(key).charAt(0));
		   }
		   else if(key.equals("portNum"))
		   {
			   theTopo.setPortNum(Integer.parseInt(prop.getProperty(key)));
			   System.out.println("portNum :"+Integer.parseInt(prop.getProperty(key)));
		   }
		   else if(key.equals("pairPortId"))
		   {
			   
			   int end=1;
			   int start=0;
			   String tempPairPort= prop.getProperty(key);
			   while(end<tempPairPort.length())
			   { 
			   start=0;  
			   end = tempPairPort.indexOf("/");
	           String onePairPortId = tempPairPort.substring(start,end);
	          // System.out.println("onePairPortId :"+onePairPortId);
	           pairPortId.add(onePairPortId);
	           tempPairPort=tempPairPort.substring(end+1);
			   }
			   theTopo.setPairPortId(pairPortId);
		   
		   }
		   else if(key.equals("portId"))
		   {
			   int end=1;
			   int start=0;
			   String tempPort= prop.getProperty(key);
			   while(end<tempPort.length())
			   { 
			   start=0;  
			   end = tempPort.indexOf("/");
	           String onePortId = tempPort.substring(start,end);
	           //System.out.println("onePortId :"+onePortId);
	           portId.add(onePortId);
	           tempPort=tempPort.substring(end+1);
			   }
			   theTopo.setPortId(portId);
		   }
		  }
		   in.close();
		   theTopo.setPower(power);
		   //System.out.println("Power :"+theTopo.getPower());
		 }
		 catch(Exception e){
			 System.out.println(e);
		 }
	}
	public String toString(){
		return "TopoInfo,nodeId:"+ theTopo.getNodeId() + ",portNum:"
	+ theTopo.getPortNum()+ ",power:"+ theTopo.getPower() + ",portId:"
	+ theTopo.getPortId().toString() + ",pairPortId:" + theTopo.getPairPortId().toString();
		
	}
}
