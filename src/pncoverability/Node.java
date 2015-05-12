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
	
	public void setVec(int[] pole){
		this.vec = new int[pole.length];
		for(int i = 0; i<pole.length ; i++){
			this.vec[i] = pole[i];
		}
	}
	
	public String printNode() {
		String text = "Uzol N" + id + " = ( ";
		for (Integer v : vec){
			if (v == -3)
				text += "omega ";
			else
				text += v.toString() + " ";
		}
		text += ")\n";
                
                return text;
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
