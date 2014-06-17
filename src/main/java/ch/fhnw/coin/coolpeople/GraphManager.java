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
import java.util.HashMap;

/**
 * Class to create the gexf extension from the underlying data structure(s)
 *
 * @author Igor Bosnjak
 * @author David Huser
 */
class GraphManager {
    private final HashMap<Person, Node> nm;
    private int counter = 0;
    String outputfile;


    /**
     * Constructor for GraphManager
     *
     * @param nm HashMap<Person, Node>
     * @param pl ArrayList<ArrayList<Person>>
     * @param gexf Gexf
     */
    public GraphManager(HashMap<Person, Node> nm, ArrayList<ArrayList<Person>> pl, Gexf gexf, String outputfile) {
        this.nm = nm;
        Gexf gx = gexf;
        this.outputfile = outputfile;
        initGraph(pl, gx, outputfile);
    }

    /**
     * Initialize graph
     *
     * @return String the actual string read from the file
     */
    private void initGraph(ArrayList<ArrayList<Person>> pl, Gexf gx, String outputfile) {

        for (ArrayList<Person> al : pl) {
            connectAllToAll(al);
        }

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        File f = new File(outputfile + "/export.gexf");
        Writer out;
        try {
            out = new FileWriter(f, false);
            graphWriter.writeToStream(gx, out, "UTF-8");
            System.out.println("GENERATING GRAPH FILE AT: " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectAllToAll(ArrayList<Person> list) {

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {

                Node from = nm.get(list.get(i));
                Node to = nm.get(list.get(j));

                if (!from.hasEdgeTo(Integer.toString(list.get(j).hashCode()))) {
                    from.connectTo(Integer.toString(counter), to);
                }

                counter++;
            }
        }
    }
}