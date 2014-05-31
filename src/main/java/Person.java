public class Person {
    private String prename, lastname;

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
}
