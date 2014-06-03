package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("SYSTEM STARTED.\n");
        new Window().setVisible(true);
        ArrayList<ArrayList<Node>> nodeList = new ArrayList<ArrayList<Node>>();

        //initialize GEXF4J Graph
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

        //read inputfiles, create documents from it, extracts names, add names to nodeList
        try {
            for(String path : Config.INPUT){
                Document doc = new Document(path);
                NameExtractor nex = new NameExtractor(doc, graph);
                nodeList.add(nex.castPersonsToNodes(nex.returnPersonArray()));
            }
            //instanciate graphmanager with the nodelist, the graph and a GEXF object
            GraphManager gm = new GraphManager(nodeList, graph, gexf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nFINISHED.");
    }
}