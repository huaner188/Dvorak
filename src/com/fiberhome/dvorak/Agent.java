package com.fiberhome.dvorak;

import java.net.ConnectException;

import com.fiberhome.dvorak.Info.Trans;

public class Agent {
	public static void main(String[] agrs) throws InterruptedException{
		//ÿ��5s����һ��
		boolean b = true;
		while(b){
			b = false;
			try {
				Trans.startTrans();
			} catch (ConnectException e) {
				e.printStackTrace();
				//System.out.println("sleep5s");
				Thread.sleep(5000);
				b = true;
			}
			
		}
		
	}
}
