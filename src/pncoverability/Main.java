package pncoverability;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.w3c.dom.Document;


public class Main {

	public static void main(String[] args) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		Component parent = null;
		if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + file.getAbsolutePath());
		  	File pflowXML = new File(file.getAbsolutePath());
			Document d= Parser.parse(pflowXML);
			Pnet net = new Pnet(d);
			
			System.out.println("Zvolen� petriho sie� obsahuje " + net.countPlaces() + " miest(a) a " + net.countTransitions() + " prechod(y/ov)");
			//net.getReachabilityTree();
			System.out.println("Je ohrani�en�? " + net.getBoundedness());
			//net.printPlaces();
			//net.printTransitions();
			
			//State state1 = net.getState();
			//state1.printPlaces();
			//State state2 = state1.fireTransition(net.getTransitionById(1));
			//state2.printPlaces();
			//state1 = state2.fireTransition(net.getTransitionById(3));
			//state1.printPlaces();
			List<Node> nodes = new LinkedList<Node>();
			int incrementID = 0;
			int lastNodeID = 0;
			
			Node nodeN0 = new Node(incrementID, 0, 0);
			int firstVec[] = new int[net.getState().getPlacesVec().length];
						for (int i = 1; i < firstVec.length; i++) {
							firstVec[i] = 0;
						}
						firstVec[0] = 1;
						
						nodeN0.setVec(firstVec);
			nodeN0.setMark("novy");
			nodeN0.setState(net.getState());
			nodes.add(nodeN0);
			
			boolean existNew = true;
			int indexPredchadzajuceho = 0;
			
			while(existNew){ // pokial existuju s oznacenim novy
				//indexPredchadzajuceho = nodes.size()-1;
				
				List<State> stavy = net.getAllPossibleStatesFrom(nodes.get(indexPredchadzajuceho).getState());
				if(stavy.isEmpty()){
					nodes.get(indexPredchadzajuceho).setMark("mrtvy");
				}
				else if (nodes.get(indexPredchadzajuceho).getMark() == "novy"){
					for (State state : stavy) {
						Node newNode = new Node(nodes.size(), nodes.get(indexPredchadzajuceho).getId(), state.getFiredTransition().getId());
						newNode.setState(state);
						newNode.setVec(state.getPlacesVec());
						//prechod nodes aby sme nasli source node
						/* nepou��va�
						Node sourceNode = null;
						for(Node node : nodes){
							if(node.getId() == nodes.get(nodes.size()-1).getSourceId())
								sourceNode = node;
						}
						*/
						//porovnanie ci je terajsi rovnaky ako source
						//TODO: omega
						int i = 0, j = 0, k = 0;
						
						//prejdem omegy a zapamatam
						int docasneOmegy[] = new int[newNode.getVec().length];
						int posuvatko = 0;
						
						for (i = 0; i < newNode.getVec().length; i++) {
							if(nodes.get(indexPredchadzajuceho).getVec()[i] == -3){
								if(i == 0)
									docasneOmegy[posuvatko] = 99;
								else
									docasneOmegy[posuvatko] = i;
								
								posuvatko++;
							}
						}
						
						int newVec[] = new int[newNode.getVec().length];
						//newVec = newNode.getVec().clone();
						for (k = 0; k < nodes.size(); k++){
							newVec = newNode.getVec().clone();
							for (i = 0; i < newNode.getVec().length; i++) {	
								if (newVec[i] > nodes.get(k).getVec()[i]) {
									newVec[i] = -3;
									j++;
								}
								else if (newVec[i] == nodes.get(k).getVec()[i])
									j++;
							}
							if (j == newNode.getVec().length){
								newNode.setVec(newVec);
								k = nodes.size();
							}
							else{
								//tu nastav spat omegy
								if(docasneOmegy.length != 0)
									for (i = 0; i < docasneOmegy.length; i++) {
										if(docasneOmegy[i] == 99)
											newNode.getVec()[0] = -3;
										else if (docasneOmegy[i] != 0)
											newNode.getVec()[docasneOmegy[i]] = -3;
									}
							}
						}
						for (k = 0; k < nodes.size(); k++) {
							j = 0;
							for (i = 0; i < newNode.getVec().length; i++)
								if (newNode.getVec()[i] == nodes.get(k).getVec()[i])
									j++;
							if(j == nodes.get(indexPredchadzajuceho).getVec().length){
								//nodes.get(nodes.size()-1).setMark("stary");
								newNode.setMark("stary");
								//System.out.println("Znacim stary");
							}
							else if (newNode.getMark() != "stary"){
								//nodes.get(nodes.size()-1).setMark("novy");
								newNode.setMark("novy");
							}
						}
						newNode.setState(state);
						nodes.add(newNode);
					}
				}
				
				int newCounter = 0;
				for(Node node : nodes){
					if(node.getMark() == "novy")
						newCounter++;
				}
				if(newCounter == 0)
					existNew = false;
				
				if (nodes.get(indexPredchadzajuceho).getMark() != "stary" && nodes.get(indexPredchadzajuceho).getMark() != "mrtvy")
				nodes.get(indexPredchadzajuceho).setMark("null");
				indexPredchadzajuceho++;
			}
			
			
			/*
			List<State> transition1 = net.getAllPossibleStatesFrom(net.getState());
			int from = nodes.get(nodes.size()-1).getId();
			
			for (State state : transition1) {
				incrementID++;
				Node node = new Node(incrementID, from, 1);
				node.setVec(state.getPlacesVec());
				nodes.add(node);
				
				List<State> transition2 = net.getAllPossibleStatesFrom(state);
				from = nodes.get(nodes.size()-1).getId();
			}
			*/
			for (Node node : nodes){
				node.printNode();
			}
			
			
			//State fire1 = net.getState();
			//fire1 = fire1.fireTransition(net.getTransitionById(1));
			//fire1 = fire1.fireTransition(net.getTransitionById(3));
			//fire1.printPlaces();
			//net.printPlaces();
			//new Coverability();
		}
		
		
	}
}
