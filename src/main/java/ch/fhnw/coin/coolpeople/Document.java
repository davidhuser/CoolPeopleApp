package ch.fhnw.coin.coolpeople;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  Document class for reading input in document object. One input file is one Document object.
 *
 * @author Igor Bosnjak
 * @author David Huser
 */
public class Document {

    private final String filename;
    private String content;

    /**
     * Constructor for Document
     *
     * @param name String filename
     */
    public Document(String name) throws IOException {
        filename = name;
        readContent();
    }

    /**
     * File to String reader
     *
     *
     */
    private void readContent() throws IOException {
        System.out.println("READING RESOURCE: " + filename);
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

    /**
     * File to String reader
     *
     * @return String the actual string read from the file
     */
    public String getContent() {
        return content;
    }
}