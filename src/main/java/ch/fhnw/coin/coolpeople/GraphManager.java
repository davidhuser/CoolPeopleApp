package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

class GraphManager {

    public GraphManager(ArrayList<ArrayList<Node>> al, Graph graph, Gexf gexf) {
        ArrayList<ArrayList<Node>> nodelist = al;
        Graph g = graph;
        Gexf gx = gexf;
        initGraph(al, g, gx);
    }

    private void initGraph(ArrayList<ArrayList<Node>> al, Graph g, Gexf gx) {

        //TODO create edges between nodes...
        //https://github.com/francesco-ficarola/gexf4j/blob/master/src/examples/java/it/uniroma1/dis/wsngroup/gexf4j/examples/StaticGexfGraph.java

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        File f = new File(Config.OUTPUTFILE);
        Writer out;
        try {
            out = new FileWriter(f, false);
            graphWriter.writeToStream(gx, out, "UTF-8");
            System.out.println(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectNodeToAll(ArrayList<Node> list, Node node) {
        int counter = 0;
        for (Node p : list) {
            counter++;
            node.connectTo(Integer.toString(counter), p);
        }
    }
}
