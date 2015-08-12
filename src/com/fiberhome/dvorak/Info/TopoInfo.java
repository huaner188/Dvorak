package com.fiberhome.dvorak.Info;

import java.util.ArrayList;

public class TopoInfo {
	private char nodeId;//�ڵ���
	private int portNum; //�˿ں�����
	private ArrayList<String> portId; //�Լ��˿ں�
	private ArrayList<Character> pairNodeId;//�Զ˽ڵ��
	private ArrayList<String> pairPortId;//�Զ˶˿ں�
	private int power; //����
	
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
