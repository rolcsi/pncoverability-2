package pncoverability;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.w3c.dom.Document;

public class Main {

    private static JTextArea textarea;

    private static void createAndShowGUI() {

        double screenWidth, screenHeight;
        final double SCREEN_FACTOR = 0.8;
        final Dimension MINIMUM_SIZE = new Dimension(200, 200);

        JFrame gui = new JFrame();

        gui.setTitle("XACML Editor");

        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gui.setExtendedState(gui.getExtendedState() | MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();

        gui.setSize(new Dimension((int) (screenWidth * SCREEN_FACTOR), (int) (screenHeight * SCREEN_FACTOR)));
        gui.setMinimumSize(MINIMUM_SIZE);
        gui.setVisible(true);

       // gui.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));

        panel.add(new JPanel());

        JButton choose = new JButton("choose .pflow");

        choose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fire();
            }
        });

        panel.add(choose);
        gui.add(panel, BorderLayout.PAGE_START);

        textarea = new JTextArea();
        textarea.setEditable(false);

        JScrollPane sp = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        Font font = textarea.getFont();
        float size = font.getSize() + 4.0f;
        textarea.setFont(font.deriveFont(size));

        gui.add(panel2, BorderLayout.WEST);
        gui.add(panel3, BorderLayout.EAST);
        gui.add(sp, BorderLayout.CENTER);

    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

    }

    private static void fire() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        Component parent = null;
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + file.getAbsolutePath());
            File pflowXML = new File(file.getAbsolutePath());
            Document d = Parser.parse(pflowXML);
            Pnet net = new Pnet(d);

            textarea.setText(null);
            textarea.append("Zvolena petriho siet obsahuje " + net.countPlaces() + " miest(a) a " + net.countTransitions() + " prechod(y/ov)\n");
            textarea.append("Je ohranicena? " + net.getBoundedness()+"\n\n");
            //System.out.println();
            //net.getReachabilityTree();
           // System.out.println();
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

            while (existNew) { // pokial existuju s oznacenim novy
                //indexPredchadzajuceho = nodes.size()-1;

                List<State> stavy = net.getAllPossibleStatesFrom(nodes.get(indexPredchadzajuceho).getState());
                if (stavy.isEmpty()) {
                    nodes.get(indexPredchadzajuceho).setMark("mrtvy");
                } else if (nodes.get(indexPredchadzajuceho).getMark() == "novy") {
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

                        int i = 0, j = 0;
                        int newVec[] = new int[newNode.getVec().length];
                        newVec = newNode.getVec().clone();

                        //prejdem omegy a zapamatam
                        int docasneOmegy[] = new int[newNode.getVec().length];
                        int posuvatko = 0;

                        for (i = 0; i < newNode.getVec().length; i++) {
                            if (nodes.get(indexPredchadzajuceho).getVec()[i] == -3) {
                                if (i == 0) {
                                    docasneOmegy[posuvatko] = 99;
                                } else {
                                    docasneOmegy[posuvatko] = i;
                                }

                                posuvatko++;
                            }
                        }

                        for (i = 0; i < newNode.getVec().length; i++) {
                            int debugovaci[] = new int[newNode.getVec().length];
                            debugovaci = nodes.get(indexPredchadzajuceho).getVec().clone();

                            if (newVec[i] > nodes.get(indexPredchadzajuceho).getVec()[i]) {
                                newVec[i] = -3;
                                j++;
                            } else if (newVec[i] == nodes.get(indexPredchadzajuceho).getVec()[i]) {
                                j++;
                            }
                        }
                        if (j == newNode.getVec().length) {
                            newNode.setVec(newVec);
                        } else {
                            //tu nastav spat omegy
                            if (docasneOmegy.length != 0) {
                                for (i = 0; i < docasneOmegy.length; i++) {
                                    if (docasneOmegy[i] == 99) {
                                        newNode.getVec()[0] = -3;
                                    } else if (docasneOmegy[i] != 0) {
                                        newNode.getVec()[docasneOmegy[i]] = -3;
                                    }
                                }
                            }
                        }
                        j = 0;
                        for (i = 0; i < newNode.getVec().length; i++) {
                            if (newNode.getVec()[i] == nodes.get(indexPredchadzajuceho).getVec()[i]) {
                                j++;
                            }
                        }
                        if (j == nodes.get(indexPredchadzajuceho).getVec().length) {
                            //nodes.get(nodes.size()-1).setMark("stary");
                            newNode.setMark("stary");
                        } else {
                            //nodes.get(nodes.size()-1).setMark("novy");
                            newNode.setMark("novy");
                        }
                        newNode.setState(state);
                        nodes.add(newNode);
                    }
                }

                int newCounter = 0;
                for (Node node : nodes) {
                    if (node.getMark() == "novy") {
                        newCounter++;
                    }
                }
                if (newCounter == 0) {
                    existNew = false;
                }

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
            for (Node node : nodes) {
                textarea.append(node.printNode());
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
