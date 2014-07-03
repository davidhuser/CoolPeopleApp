import ch.fhnw.coin.coolpeople.Textfile;
import ch.fhnw.coin.coolpeople.NameExtractor;
import ch.fhnw.coin.coolpeople.Person;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;

public class NameExtractorTest {

    private final String names = new Scanner(getClass().getResourceAsStream("/person_first_name.lst"), "UTF-8").useDelimiter("\\A").next();
    private static NameExtractor nex;
    private static Person p;
    private File tempfile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void createTestData() throws IOException {
        tempfile = folder.newFile("temp.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(tempfile));
        String filecontent = "With a remarkable record of achievement, " +
                "General Michael Henninger has been praised for creating a revolution in warfare that fused " +
                "intelligence and operations. A four-star general, he is the former commander of U.S. and international " +
                "forces in Afghanistan and the former leader of Joint Special Operations Command (JSOC), which " +
                "oversees the military's most sensitive forces. Henninger's leadership of JSOC is credited with the " +
                "December 2003 capture of Peter Gloor and the June 2006 location and killing of Michael Henninger, the " +
                "leader of al-Qaeda in Iraq. Henninger, a former Green Beret, is known for his candor.";
        out.write(filecontent);
        out.close();
        Textfile doc = new Textfile(tempfile.getAbsolutePath());
        Gexf gexf = new GexfImpl();
        Graph graph = gexf.getGraph();
        nex = new NameExtractor(doc, graph);
        p = new Person("Michael", "Henninger");
    }

    @Test
    public void testExtractNames(){
        Person[] arr = nex.returnPersonArray().toArray(new Person[nex.returnPersonArray().size()]);
        Assert.assertThat(arr, hasItemInArray(p));
    }

    @Test
    public void testIsInPersonList(){

        ArrayList<Person> personList = new ArrayList<Person>();
        Assert.assertEquals(0, personList.size());
        personList.add(p);
        Assert.assertEquals(1, personList.size());
        Assert.assertEquals(personList.get(0), p);
        personList.remove(0);
        Assert.assertEquals(0, personList.size());

    }

    @Test
    public void testHasPersonWithLastName(){
        String lastname = "Henninger";
        Assert.assertEquals(p.getLastname(), lastname);
    }

    @Test
    public void testHasPersonWithPreName(){
        String prename = "Michael";
        Assert.assertEquals(p.getPrename(), prename);
    }

    @Test
    public void testListContainsRealNames() {

        final String name = "Franz";
        Assert.assertThat(names, notNullValue());
        Assert.assertThat(names, containsString(name));
    }

    @Test(expected = AssertionError.class)
    public void testListDoesNotContainsRandomStrings() {

        final String noName = "Xdkazz";
        Assert.assertThat(names, containsString(noName));
    }
}
