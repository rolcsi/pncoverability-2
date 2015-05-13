package pncoverability;

import java.util.LinkedList;
import java.util.List;

//import org.w3c.dom.Node;

public class Coverability {
	public LinkedList<Node> nodes;
	
	public Coverability() {
		//this.nodes = new LinkedList<Node>();
		
		/* TEST */
		//Node n = new Node(1, -1, -1);
		//n.addTkn(4); n.addTkn(3); n.addTkn(0); n.addTkn(-1);
		//n.printNode();
		
		//nodes.add(n);
		
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
	
	public static void testTrLiveness(List<Transition> transitions, List<pncoverability.Node> nodes) {
		for (Transition t : transitions) {
			t.setLive(false);
			for (Node n : nodes) {
				if (t.getLabel() == n.getSourceTrLabel())
					t.setLive(true);
			}
		}
	}
}
