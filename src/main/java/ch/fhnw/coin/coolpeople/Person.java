package ch.fhnw.coin.coolpeople;

public class Person {
    private final String prename;
    private final String lastname;

    public Person(String prename, String lastname) {
        this.prename = prename;
        this.lastname = lastname;
    }

    public String getPrename() {
        return prename;
    }

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
        if (!prename.equals(person.prename)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prename.hashCode();
        result = 31 * result + lastname.hashCode();
        return result;
    }
}
