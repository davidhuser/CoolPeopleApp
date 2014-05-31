package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;

class GraphManager {

    private final ArrayList<ArrayList<Person>> graph;

    public GraphManager(ArrayList<ArrayList<Person>> al) {
        this.graph = al;
        initGraph(graph);
    }

    private void initGraph(ArrayList<ArrayList<Person>> al) {

        Gexf gexf = new GexfImpl();
        Calendar date = Calendar.getInstance();

        gexf.getMetadata()
                .setLastModified(date.getTime())
                .setCreator("CoolPeopleApp")
                .setDescription("Document Network");
        gexf.setVisualization(true);

        Graph graph = gexf.getGraph();
        graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        //TODO links created nodes to edges depending on source document
        int counter = 0;
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for (ArrayList<Person> innerList : al) {
            for (Person p : innerList) {
                Node node = graph.createNode(Integer.toString(counter));
                node.setLabel(p.toString());
                nodeList.add(node);
                connectNodeToAll(nodeList, node);
                counter++;
            }
        }

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        File f = new File(Config.OUTPUTFILE);
        Writer out;
        try {
            out = new FileWriter(f, false);
            graphWriter.writeToStream(gexf, out, "UTF-8");
            System.out.println("GRAPH FILE CREATED AT:");
            System.out.println(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connectNodeToAll(ArrayList<Node> list, Node node){
        int counter = 0;
        for(Node p: list){
            counter++;
            node.connectTo(Integer.toString(counter), p);
        }
    }
}
