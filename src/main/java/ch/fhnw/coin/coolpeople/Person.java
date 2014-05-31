package ch.fhnw.coin.coolpeople;

class Person {
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
}
