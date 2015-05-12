package pncoverability;

import java.util.LinkedList;
import java.util.List;

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
	
	public static void testSafety(Pnet petrinet, List<Node> nodes) {
		int i;
		petrinet.setSafety(true);
		for (Node n : nodes)
			for (i = 0; i < n.getVec().length; i++)
				if (n.getVec()[i] != 0 && n.getVec()[i] != 1)
					petrinet.setSafety(false);
	}
}
