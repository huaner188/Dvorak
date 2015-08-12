package com.fiberhome.dvorak.Info;

import java.util.ArrayList;

public class UploadInfo {
	private char nodeId;//上报节点ID
	
	private boolean status;//状态
	private ArrayList<Integer> flowId;//  仅A,C用到
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