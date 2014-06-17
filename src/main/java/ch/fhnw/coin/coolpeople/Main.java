package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;

import java.io.IOException;
import java.util.*;

public class Main {
    public static ArrayList<Person> templist;

    public static void main(String[] args) throws IOException {
        String outputfile = "not set";
        ArrayList<String> inputfiles = new ArrayList<String>();

        if (args.length == 0) {

        }

        int length = args.length;
        if (length <= 0) {
            System.out.println("no arguments were given. First argument: export folder path, " +
                    "all other following arguments: path to text files");
        }
        for (int i = 0; i < length; i++) {
            if(args[i] == args[0]) {
                outputfile = args[0];
            }else{
                inputfiles.add(args[i]);
            }
        }
        System.out.println(inputfiles.toString());

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



        System.out.println(inputfiles.toString());

        //read all inputfiles, create documents from it, extracts names, add names to nodeList
        try {
            for(String path : inputfiles){
                //create Document object
                Document doc = new Document(path);
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
            //instanciate graphmanager with the nodelists and a GEXF object
            GraphManager gm = new GraphManager(nodemap, personlist, gexf, outputfile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nFINISHED.");
    }
}