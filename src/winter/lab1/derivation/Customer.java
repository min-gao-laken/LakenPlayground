package winter.lab1.derivation;

/**
 * @author Laken
 * @date 2026-01-27
 * @description
 */
public class Customer extends Person {
    private int customerId;

    // constructor
    public Customer() {
    }

    public Customer(int customerId, String firstName, String lastName, char middleInit) {
        // call super()
        super(firstName, lastName, middleInit);

        this.customerId = customerId;
    }

    // getter and setter
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    /**
     *
     * @return customer and person information in the format of the txt file
     */
    public String writeAsRecord() {
        return "xx"; // todo
    }


    /**
     *
     * @return the details of the customer for display
     */
    @Override
    public String toString() {
        return getFirstName(); // todo
    }

//    public static void main(String[] args) {
//
//    }
}
