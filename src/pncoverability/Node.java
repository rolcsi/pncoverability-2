package pncoverability;

import java.util.LinkedList;

public class Node {
	private int id, sourceId, sourceTr;
	private int[] vec;
	private String mark, sourceTrLabel;
	private State state;
	
	public Node(int id, int sourceId, int sourceTr, String sourceTrLabel) {
		this.id = id;
		this.sourceId = sourceId;
		this.sourceTr = sourceTr;
		this.sourceTrLabel = sourceTrLabel;
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
		//System.out.println(text + ") " + this.mark);
                if (this.mark != "null")
                	text += ") \"" + this.mark + "\" z uzla N" + this.sourceId + " prechodom \"" + this.sourceTrLabel +"\"\n";
                else
                	text += ")" + " z uzla N" + this.sourceId + " prechodom \"" + this.sourceTrLabel +"\"\n";
                
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
	public String getSourceTrLabel() {
		return sourceTrLabel;
	}
}