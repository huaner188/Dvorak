package com.fiberhome.dvorak.Info;

import java.util.ArrayList;

public class TopoInfo {
	private char nodeId;//节点编号
	private int portNum; //端口号总数
	private ArrayList<String> portId; //自己端口号
	private ArrayList<Character> pairNodeId;//对端节点号
	private ArrayList<String> pairPortId;//对端端口号
	private int power; //功耗
	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public char getNodeId() {
		return nodeId;
	}
	public void setNodeId(char nodeId) {
		this.nodeId = nodeId;
	}
	public int getPortNum() {
		return portNum;
	}
	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}
	public ArrayList<String> getPortId() {
		return portId;
	}
	public void setPortId(ArrayList<String> portId) {
		this.portId = portId;
	}
	public ArrayList<String> getPairPortId() {
		return pairPortId;
	}
	public void setPairPortId(ArrayList<String> pairPortId) {
		this.pairPortId = pairPortId;
	}
}
