import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Document {

    private String filename;
    private String content;

    public Document(String name) throws IOException {
        filename = name;
        readContent();
    }

    private void readContent() throws IOException {
        System.out.println("READING RESOURCE...");
        // Create an input stream and file channel
        FileInputStream fis = new FileInputStream(filename);
        FileChannel fc = fis.getChannel();

        // Read the contents of a file into a ByteBuffer
        ByteBuffer bb = ByteBuffer.allocate((int) fc.size());
        fc.read(bb);
        fc.close();

        // Convert ByteBuffer to one long String
        content = new String(bb.array());
    }

    public String getContent(){
        return content;
    }
}