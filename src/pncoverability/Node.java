package pncoverability;

import java.util.LinkedList;

public class Node {
	private int id, sourceId, sourceTr;
	private int[] vec;
	private String mark;
	private State state;
	
	public Node(int id, int sourceId, int sourceTr) {
		this.id = id;
		this.sourceId = sourceId;
		this.sourceTr = sourceTr;
		this.vec = new int[3];
		this.vec[0] = 0;
		this.vec[1] = 0;
		this.vec[2] = 0;
		
	}
	
	public Node() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	
	public int getSourceId() {
		return sourceId;
	}
	
	public int getSourceTr() {
		return sourceTr;
	}
	
	public int[] getVec() {
		return vec;
	}
	
	public void setVec(int value1, int value2, int value3) {
		this.vec[0] = value1;
		this.vec[1] = value2;
		this.vec[2] = value3;
	}
	
	public void setVec(int[] pole){
		this.vec[0] = pole[0];
		this.vec[1] = pole[1];
		this.vec[2] = pole[2];
	}
	
	public void printNode() {
		String text = "Uzol N" + id + " = ( ";
		for (Integer v : vec){
			if (v == -3)
				text += "omega ";
			else
				text += v.toString() + " ";
		}
		System.out.println(text + ") ");
	}
	
	public String getMark(){
		return this.mark;
	}
	
	public void setMark(String mark){
		this.mark = mark;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
