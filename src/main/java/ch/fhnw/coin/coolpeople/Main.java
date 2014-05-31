package ch.fhnw.coin.coolpeople;

import java.io.IOException;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("SYSTEM STARTED.");

        ArrayList<ArrayList<Person>> graph = new ArrayList<ArrayList<Person>>();

        try {
            Document doc1 = new Document(Constants.BASE_DIR + "inputtext_eng.txt");
            NameExtractor nex1 = new NameExtractor(doc1);

            Document doc2 = new Document(Constants.BASE_DIR + "inputtext_eng2.txt");
            NameExtractor nex2 = new NameExtractor(doc2);

            graph.add(nex1.returnPersonArray());
            graph.add(nex2.returnPersonArray());

            GraphManager gm = new GraphManager(graph);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nFINISHED.");

    }
}