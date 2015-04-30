package pncoverability;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Pnet {

	private State init;
	private List<Transition> transitions;
	private boolean isBounded;
	
	public Pnet(Document d) {
		init = new State();
		transitions = new LinkedList<Transition>();

		NodeList placeList = d.getElementsByTagName("place");
		NodeList transList = d.getElementsByTagName("transition");
		NodeList arcList = d.getElementsByTagName("arc");

		for (int i = 0; i < placeList.getLength(); i++) {
			Node n = placeList.item(i);
			NodeList nl = n.getChildNodes();
			
			int tempId=-1,tempToken=-1;
			String tempLabel = "";
			
			for (int j = 0; j < nl.getLength(); j++) {
				Node atr = nl.item(j);

				if (atr instanceof Element) {
					Node value = atr.getLastChild();
					if (value != null)
					{
						String content = value.getTextContent().trim();
						switch (atr.getNodeName()) {
							case "id":
								tempId = Integer.parseInt(content);
								break;
							case "tokens":
								tempToken = Integer.parseInt(content);
								break;
							case "label":
								tempLabel = content;
								break;
						}	
					}
				}
			}
			Place pl = new Place(tempId,tempToken, tempLabel);
			init.getStatePlaces().addPlace(pl);
		}

		for (int i = 0; i < transList.getLength(); i++) {
			Node n = transList.item(i);
			NodeList nl = n.getChildNodes();
			
			int tempId=-1;
			String tempLabel = "";
			
			for (int j = 0; j < nl.getLength(); j++) {
				Node atr = nl.item(j);
				if (atr instanceof Element) {
					Node value = atr.getLastChild();
					if (value != null)
					{
						String content = value.getTextContent().trim();
						switch (atr.getNodeName()) {
							case "id":
								tempId = Integer.parseInt(content);
								break;
							case "label":
								tempLabel = content;
								break;
						}
					}
				}
			}
			Transition tr = new Transition(tempId, tempLabel);
			transitions.add(tr);
		}

		for (int i = 0; i < arcList.getLength(); i++) {
			Node n = arcList.item(i);
			NodeList nl = n.getChildNodes();
			int idIn = -1, idOut = -1, weight = -1;

			for (int j = 0; j < nl.getLength(); j++) {
				Node atr = nl.item(j);

				if (atr instanceof Element) {
					Node value = atr.getLastChild();
					if (value != null)
					{
						String content = value.getTextContent().trim();
						switch (atr.getNodeName()) {
							case "sourceId":
								idIn = Integer.parseInt(content);
								break;
							case "destinationId":
								idOut = Integer.parseInt(content);
								break;
							case "multiplicity":
								weight = Integer.parseInt(content);
								break;
						}			
					}
				}
			}

			if (idIn == -1)
				return;

			Place p = init.getStatePlaces().getPlaceById(idIn);

			if (p == null) {
				Transition t = getTransitionById(idIn);
				p = init.getStatePlaces().getPlaceById(idOut);
				t.addOut(p.getId(), weight); // mo�no prehodi�

			} 
			else {
				Transition t = getTransitionById(idOut);
				t.addIn(p.getId(), weight); // s t�mto
			}
		}
	}

	public Transition getTransitionById(int id) {
		for (Transition tr : transitions) {
			if (tr.getId() == id)
				return tr;
		}
		return null;
	}

	public void getReachabilityTree() {
		Queue<State> nStates = new LinkedList<State>();
		List<State> allreadyChecked = new LinkedList<State>();

		nStates.add(init);

		while (!nStates.isEmpty()) {
			State topState = nStates.remove();
			//System.out.println(topState.getStatePlaces().getPlaces());
			List<State> afterTransitions = getAllPossibleStatesFrom(topState);

			for (State state : afterTransitions) {
				if (state.isBiggerThanOneOfParents()){
					isBounded = false;
					return;
				}
				if (!isChecked(state, allreadyChecked)) {
					
					nStates.add(state);
					allreadyChecked.add(state);
				}
			}

		} 
		isBounded = true;
	}

	public boolean getBoundedness( ) {
		return isBounded;
	}
	
	public int countPlaces() {
		return this.init.getStatePlaces().countPlaces();
	}
	
	public int countTransitions() {
		return transitions.size();
	}
	
	private boolean isChecked(State stat, List<State> list) {
		for (State state : list) {
			if(state.isSameAs(stat)) return true;
		}
		return false;
	}
	public State getState(){
		return init;
	}
	
	public void printPlaces() {
		init.printPlaces();
	}
	
	public void printTransitions() {
		for (Transition t : transitions) {
			System.out.println("Prechod ID: " + t.getId());
			System.out.println("-> Hrany vstupn� "+ t.getIn().toString() + ", v�stupn� " + t.getOut().toString());
		}
	}

	public List<State> getAllPossibleStatesFrom(State s) {
		List<State> afterTrans = new LinkedList<State>();

		for (Transition transition : transitions) {
			State afterState = s.fireTransition(transition);
			if (afterState != null)
				afterTrans.add(afterState);			
		}
		return afterTrans;
	}
}
