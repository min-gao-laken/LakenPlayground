package winter.lab1.polymorphism.derivation;

/**
 * @author Laken
 * @date 2026-01-27
 * @description
 */
public class Employee extends Person {
    private int id;
    private String position;


    public Employee(String firstName, String lastName, char middleInit, int id, String position) {
        // super calls the constructor of the parent class
        super(firstName, lastName, middleInit); // past them to parent class

        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // @Overrider means I know what I am doing.
    // If you add @Overrider, it will tell you that you are wrong
    @Override
    public String toString() {
        return id + ": " + getLastName() + ", " + getFirstName() + ".";
    }
}
