package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

class GraphManager {
    private final HashMap<Person, Node> nm;
    private int counter = 0;

    public GraphManager(HashMap<Person, Node> nm, ArrayList<ArrayList<Person>> pl, Graph graph, Gexf gexf) {
        this.nm = nm;
        Graph g = graph;
        Gexf gx = gexf;
        initGraph(pl, g, gx);
    }

    private void initGraph(ArrayList<ArrayList<Person>> pl, Graph g, Gexf gx) {

        for(ArrayList<Person> al : pl){
            connectNodeToAll(al);
        }

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

    private void connectNodeToAll(ArrayList<Person> list) {

        for(int i=0; i<list.size()-1; i++){
            for(int j=i+1; j<list.size(); j++){
                Node from = nm.get(list.get(i));
                Node to = nm.get(list.get(j));
                if(from == null || to == null){
                    System.out.println("nulll  " + counter);
                }
                if(!from.hasEdgeTo(Integer.toString(list.get(j).hashCode()))) {
                    from.connectTo(Integer.toString(counter), to);
                }
                counter++;
            }
        }
    }
}