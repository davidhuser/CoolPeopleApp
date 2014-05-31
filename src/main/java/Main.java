import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("SYSTEM STARTED.");

        try {
            Document doc1 = new Document("/Users/david/Desktop/inputtext_eng.txt");
            NameExtractor nex1 = new NameExtractor(doc1);

            System.out.println(nex1.returnPersonArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nFINISHED.");

    }
}