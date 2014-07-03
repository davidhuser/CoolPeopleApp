import ch.fhnw.coin.coolpeople.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DocumentTest {
    private static final String filename = "temp.txt";
    private static final File TEMP_FILE = new File(filename);

    @Before
    public void setUp() throws Exception {
        FileWriter writer = null;
        try {
            writer = new FileWriter(TEMP_FILE);
            writer.write("Hello\nworld");
        } finally {
            writer.close();
        }
    }

    @After
    public void tearDown() throws Exception {
        TEMP_FILE.delete();
    }

    @Test
    public void testReadContent() throws IOException {
        Assert.assertEquals(11, new Textfile(filename).getContent().length());
    }


}


