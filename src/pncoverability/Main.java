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
			
			System.out.println("Zvolen· petriho sieù obsahuje " + net.countPlaces() + " miest(a) a " + net.countTransitions() + " prechod(y/ov)");
			//net.getReachabilityTree();
			System.out.println("Je ohraniËen·? " + net.getBoundedness());
			//net.printPlaces();
			//net.printTransitions();
			
			State state1 = net.getState();
			//state1.printPlaces();
			State state2 = state1.fireTransition(net.getTransitionById(1));
			//state2.printPlaces();
			state1 = state2.fireTransition(net.getTransitionById(3));
			//state1.printPlaces();
			List<Node> nodes = new LinkedList<Node>();
			int incrementID = 0;
			int lastNodeID = 0;
			
			Node nodeN0 = new Node(incrementID, 0, 0);
			nodeN0.setVec(1, 0, 0);
			nodeN0.setMark("novy");
			nodeN0.setState(net.getState());
			nodes.add(nodeN0);
			
			boolean existNew = true;
			int indexPredchadzajuceho = 0;
			
			while(existNew){ // pokial existuju s oznacenim novy
				indexPredchadzajuceho = nodes.size()-1;
				
				List<State> stavy = net.getAllPossibleStatesFrom(nodes.get(indexPredchadzajuceho).getState());
				if(stavy.isEmpty()){
					nodes.get(indexPredchadzajuceho).setMark("mrtvy");
				}
				else{
					
					for (State state : stavy) {
						Node newNode = new Node(nodes.size()+1, nodes.get(indexPredchadzajuceho).getId(), 1);
						newNode.setState(state);
						//prechod nodes aby sme nasli source node
						/*
						Node sourceNode = null;
						for(Node node : nodes){
							if(node.getId() == nodes.get(nodes.size()-1).getSourceId())
								sourceNode = node;
						}
						*/
						//porovnanie ci je terajsi rovnaky ako source
						//TODO: omega
						
						if(newNode.getVec().equals(nodes.get(indexPredchadzajuceho).getVec())){
							nodes.get(nodes.size()-1).setMark("stary");
						}
						else
							nodes.get(nodes.size()-1).setMark("novy");
						
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
				
				nodes.get(indexPredchadzajuceho).setMark("null");
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
			
			
			State fire1 = net.getState();
			fire1 = fire1.fireTransition(net.getTransitionById(1));
			fire1 = fire1.fireTransition(net.getTransitionById(3));
			//fire1.printPlaces();
			//net.printPlaces();
			//new Coverability();
		}
		
		
	}
}
