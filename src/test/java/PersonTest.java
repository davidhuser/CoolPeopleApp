import ch.fhnw.coin.coolpeople.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testGetPrename(){
        String prename = "Vorname";
        String lastname = "Nachname";
        Person person = new Person(prename, lastname);
        assertEquals(prename, person.getPrename());
    }

    @Test
    public void testGetLastname(){
        String prename = "Vorname";
        String lastname = "Nachname";
        Person person = new Person(prename, lastname);
        assertEquals(lastname, person.getLastname());
    }

    @Test
    public void testGetFullName(){
        String prename = "Vorname";
        String lastname = "Nachname";
        String fullname = prename + " " + lastname;
        Person person = new Person(prename, lastname);
        assertEquals(fullname, person.toString());
    }

}