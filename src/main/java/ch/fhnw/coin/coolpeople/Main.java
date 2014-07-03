package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;

import java.io.IOException;
import java.util.*;

public class Main {
    private static ArrayList<Person> templist;
    private static ArrayList<Person> tempwikilist;

    public static void main(String[] args) throws IOException {
        System.out.println("SYSTEM STARTED.\n");
        //Window w = new Window();
        //w.setVisible(true);
        ArrayList<ArrayList<Person>> personlist = new ArrayList<ArrayList<Person>>();
        ArrayList<ArrayList<Node>> nodeList = new ArrayList<ArrayList<Node>>();
        HashMap<Person, Node> nodemap = new HashMap<Person, Node>();


        //initialize undirected GEXF4J graph
        Gexf gexf = new GexfImpl();
        Calendar date = Calendar.getInstance();

        gexf.getMetadata()
                .setLastModified(date.getTime())
                .setCreator("CoolPeopleApp")
                .setDescription("Document_Network");
        gexf.setVisualization(true);

        Graph graph = gexf.getGraph();
        graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        //read all inputfiles, create documents from it, extracts names, add names to nodeList
        try {
            if(!Config.TEXTFILES.isEmpty()) {
                for (String path : Config.TEXTFILES) {
                    //create Document object
                    Textfile doc = new Textfile(path);
                    System.out.println("r:" + path);
                    //extract names
                    NameExtractor nex = new NameExtractor(doc, graph);

                    //get list for filtering with GUI
                    templist = nex.returnPersonArray();
                    //w.integratedpersonList(templist);

                    //add to personlist of all documents
                    personlist.add(templist);

                    //cast person list to node list, which is compatible with GEXF library
                    nodeList.add(nex.castPersonsToNodes(templist));

                    //put all nodes in a HashMap of Person to Node
                    nodemap.putAll(nex.getNodemap());
                }
            }

            else if(!Config.URLS.isEmpty()) {
                for (String path : Config.URLS) {
                    Wikipage page = new Wikipage(path);
                    NameExtractor nex2 = new NameExtractor(page, graph);
                    tempwikilist = nex2.returnPersonArray();
                    personlist.add(tempwikilist);
                    nodeList.add(nex2.castPersonsToNodes(tempwikilist));
                    nodemap.putAll(nex2.getNodemap());

                }
            }else{
                System.out.println("No input data provided in File Config.java");
            }
            //instanciate graphmanager with the nodelists and a GEXF object
            GraphManager gm = new GraphManager(nodemap, personlist, gexf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nFINISHED.");
    }
}