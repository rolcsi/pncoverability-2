package pncoverability;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Document;

public class Main {

    private static JTextArea textarea;

    private static JLabel ohran;
    private static JPanel left;
    private static JFrame gui;
    private static JLabel name, analytext, vypisy, bezp;

    private static void createAndShowGUI() {

        double screenWidth, screenHeight;
        final double SCREEN_FACTOR = 0.8;
        final Dimension MINIMUM_SIZE = new Dimension(200, 200);

        gui = new JFrame();

        gui.setTitle("Coverability Analyzer");

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
        panel.setLayout(new GridLayout(1, 1));

        JButton choose = new JButton("choose .pflow");

        choose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fire();
            }
        });
        
        EmptyBorder title = new EmptyBorder(new Insets(10, 20, 15, 20));
       
        JLabel tit = new JLabel("<html><font size='7'>Coverability Analyzer</font></html>", SwingConstants.CENTER);
        tit.setBorder(title);

        //panel.add(choose);
        panel.add(tit);
        /*panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());*/

        ohran = new JLabel();

     /*   panel.add(ohran);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());*/
        
        bezp = new JLabel();
        
        /*panel.add(bezp);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());

        gui.add(panel, BorderLayout.PAGE_START);*/
        
        gui.add(panel, BorderLayout.PAGE_START);

        EmptyBorder eb = new EmptyBorder(new Insets(0, 20, 0, 20));
        //panel.setBorder(eb);

        /*  JTextPane tPane = new JTextPane();                
         tPane.setBorder(eb);
         //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
         tPane.setMargin(new Insets(5, 5, 5, 5));

         topPanel.add(tPane);

         appendToPane(tPane, "My Name is Too Good.\n", Color.RED);
         appendToPane(tPane, "I wish I could be ONE of THE BEST on ", Color.BLUE);
         appendToPane(tPane, "Stack", Color.DARK_GRAY);
         appendToPane(tPane, "Over", Color.MAGENTA);
         appendToPane(tPane, "flow", Color.ORANGE);*/
        textarea = new JTextArea();
        textarea.setEditable(false);

        JScrollPane sp = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        EmptyBorder eb2 = new EmptyBorder(new Insets(10, 20, 20, 20));
        EmptyBorder eb3 = new EmptyBorder(new Insets(10, 20, 20, 20));
        EmptyBorder eb4 = new EmptyBorder(new Insets(10, 10, 0, 10));
        sp.setBorder(eb2);

        JPanel body = new JPanel();
        body.setLayout(new GridLayout(1, 2));
        left = new JPanel();
        left.setBorder(eb3);

        Font font = textarea.getFont();
        float size = font.getSize() + 4.0f;
        textarea.setFont(font.deriveFont(size));
        
        textarea.setBorder(eb4);
        
        left.setLayout(new GridLayout(10, 1));
        
        JPanel choosepanel = new JPanel();
        choosepanel.setLayout(new GridLayout(2, 2));
        choosepanel.add(choose);
             
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());
        choosepanel.add(new JPanel());

        
        left.add(choosepanel);
        
        
        body.add(left);
        body.add(sp);

        /*  gui.add(panel2, BorderLayout.WEST);
         gui.add(panel3, BorderLayout.EAST);*/
        gui.add(body, BorderLayout.CENTER);
        
        name = new JLabel();
        left.add(name);
        
         analytext = new JLabel();
           // analytext.setBorder(analy);
            
            left.add(analytext);
            
            vypisy = new JLabel();
            left.add(vypisy);
            
            bezp = new JLabel();
            left.add(bezp);

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
            //System.out.println("Selected file: " + file.getAbsolutePath());
            
            name.setText("<html><font size='4'>name: "+file.getAbsolutePath()+" </font></html>");
            
            
            
           // EmptyBorder analy = new EmptyBorder(new Insets(80, 80, 80, 80));
         
            analytext.setText("<html><font size='10'>Analysis</font></html>");
           
            
            File pflowXML = new File(file.getAbsolutePath());
            Document d = Parser.parse(pflowXML);
            Pnet net = new Pnet(d);

            textarea.setText(null);
            textarea.append("Zvolena petriho siet obsahuje " + net.countPlaces() + " miest(a) a " + net.countTransitions() + " prechod(y/ov)\n\n");

            //textarea.append("<html>Je ohranicena?: <font color='red'>" + net.getBoundedness() + "</font></html>\n");

            net.getReachabilityTree();
            
            
            
            if (net.getBoundedness()) {
                vypisy.setText("<html><font size='5'>Je ohranicena?: </font><font color='green' size='5'>ANO</font></html>\n");
            }else{
                vypisy.setText("<html><font size='5'>Je ohranicena?: </font><font color='red' size='5'>NIE</font></html>\n");
            }
                                 
        //System.out.println("Zvolena petriho siet obsahuje " + net.countPlaces() + " miest(a) a " + net.countTransitions() + " prechod(y/ov)");
            //net.getReachabilityTree();
            // System.out.println("Je ohranicena? " + net.getBoundedness());
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

            Node nodeN0 = new Node(incrementID, 0, 0, "null");
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
                        Node newNode = new Node(nodes.size(), nodes.get(indexPredchadzajuceho).getId(), state.getFiredTransition().getId(), state.getFiredTransition().getLabel());
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
                            if (nodes.get(indexPredchadzajuceho).getVec()[i] == -3) {
                                if (i == 0) {
                                    docasneOmegy[posuvatko] = 99;
                                } else {
                                    docasneOmegy[posuvatko] = i;
                                }

                                posuvatko++;
                            }
                        }

                        int newVec[] = new int[newNode.getVec().length];
                        //newVec = newNode.getVec().clone();
                        for (k = 0; k < nodes.size(); k++) {
                            newVec = newNode.getVec().clone();
                            for (i = 0; i < newNode.getVec().length; i++) {
                                if (newVec[i] > nodes.get(k).getVec()[i]) {
                                    newVec[i] = -3;
                                    j++;
                                } else if (newVec[i] == nodes.get(k).getVec()[i]) {
                                    j++;
                                }
                            }
                            if (j == newNode.getVec().length) {
                                newNode.setVec(newVec);
                                k = nodes.size();
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
                        }
                        for (k = 0; k < nodes.size(); k++) {
                            j = 0;
                            for (i = 0; i < newNode.getVec().length; i++) {
                                if (newNode.getVec()[i] == nodes.get(k).getVec()[i]) {
                                    j++;
                                }
                            }
                            if (j == nodes.get(indexPredchadzajuceho).getVec().length) {
                                //nodes.get(nodes.size()-1).setMark("stary");
                                newNode.setMark("stary");
                                //System.out.println("Znacim stary");
                            } else if (newNode.getMark() != "stary") {
                                //nodes.get(nodes.size()-1).setMark("novy");
                                newNode.setMark("novy");
                            }
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

                if (nodes.get(indexPredchadzajuceho).getMark() != "stary" && nodes.get(indexPredchadzajuceho).getMark() != "mrtvy") {
                    nodes.get(indexPredchadzajuceho).setMark("null");
                }
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
            Coverability.testSafety(net, nodes);
            textarea.append(net.testTrLiveness(nodes));
            
            //System.out.println("Bezpecnost: " + net.getSafety());
            
            
            
            if (net.getSafety()) {
                bezp.setText("<html><font size='5'>Je bezpecna?: </font><font color='green' size='5'>ANO</font></html>\n");
            }else{
                bezp.setText("<html><font size='5'>Je bezpecna?: </font><font color='red' size='5'>NIE</font></html>\n");
            }
            
            gui.revalidate();
        }
    }
}
