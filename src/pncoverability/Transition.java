package pncoverability;

import java.util.HashMap;

public class Transition {

	private int id;
	private String label;
	private HashMap<Integer, Integer> in, out; // id, váha hrany
	private boolean isLive;
	
	public Transition(int id, String label) {
		this.id = id;
		this.label = label;
		this.in = new HashMap<Integer, Integer>();
		this.out = new HashMap<Integer, Integer>();
	}
	
	public void addIn(int id, int weight) {
		this.in.put(id, weight);
	}
	
	public void addOut(int id, int weight) {
		this.out.put(id, weight);
	}
	
	public HashMap<Integer, Integer> getIn() {
		return in;
	}
	
	public HashMap<Integer, Integer> getOut() {
		return out;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setIn(HashMap<Integer, Integer> in) {
		this.in = in;
	}
	
	public void setOut(HashMap<Integer, Integer> out) {
		this.out = out;
	}

	public boolean getisLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
}
