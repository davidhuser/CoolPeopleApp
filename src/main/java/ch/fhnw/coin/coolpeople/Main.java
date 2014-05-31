package ch.fhnw.coin.coolpeople;

import java.io.IOException;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("SYSTEM STARTED.\n");

        ArrayList<ArrayList<Person>> graph = new ArrayList<ArrayList<Person>>();

        try {
            Document doc1 = new Document(Config.TXT1);
            NameExtractor nex1 = new NameExtractor(doc1);

            Document doc2 = new Document(Config.TXT2);
            NameExtractor nex2 = new NameExtractor(doc2);

            graph.add(nex1.returnPersonArray());
            graph.add(nex2.returnPersonArray());

            GraphManager gm = new GraphManager(graph);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nFINISHED.");

    }
}