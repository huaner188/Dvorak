package com.fiberhome.dvorak.Info;

import java.util.ArrayList;

public class UploadInfo {
	private char nodeId;//�ϱ��ڵ�ID
	
	private boolean status;//״̬
	private ArrayList<Integer> flowId;//  ��A,C�õ�
	private ArrayList<Port> port ;
	
	public char getNodeId() {
		return nodeId;
	}
	public void setNodeId(char nodeId) {
		this.nodeId = nodeId;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ArrayList<Port> getPort() {
		return port;
	}
	public void setPort(ArrayList<Port> port) {
		this.port = port;
	}

	public ArrayList<Integer> getFlowId() {
		return flowId;
	}
	public void setFlowId(ArrayList<Integer> flowId) {
		this.flowId = flowId;
	}



	

}