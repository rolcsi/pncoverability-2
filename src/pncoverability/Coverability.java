package pncoverability;

import java.util.LinkedList;

public class Coverability {
	public LinkedList<Node> nodes;
	
	public Coverability() {
		this.nodes = new LinkedList<Node>();
		
		/* TEST */
		Node n = new Node(1, -1, -1);
		//n.addTkn(4); n.addTkn(3); n.addTkn(0); n.addTkn(-1);
		//n.printNode();
		
		nodes.add(n);
		
		/* KONIEC TESTU */
	}
}
