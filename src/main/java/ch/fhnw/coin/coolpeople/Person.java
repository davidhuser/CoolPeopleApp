package ch.fhnw.coin.coolpeople;

/**
 * Represenation of a single person
 *
 * @author Igor Bosnjak
 * @author David Huser
 */
public class Person {
    private final String prename;
    private final String lastname;

    /**
     * Constructor for Person
     *
     * @param prename String
     * @param lastname String
     */
    public Person(String prename, String lastname) {
        this.prename = prename;
        this.lastname = lastname;
    }

    /**
     * Getter for prename
     *
     * @return String prename
     */
    public String getPrename() {
        return prename;
    }

    /**
     * Getter for last name
     *
     * @return String lastname
     */
    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return (this.getPrename() + " " + this.getLastname());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!lastname.equals(person.lastname)) return false;
        return prename.equals(person.prename);

    }

    @Override
    public int hashCode() {
        int result = prename.hashCode();
        result = 31 * result + lastname.hashCode();
        return result;
    }
}
