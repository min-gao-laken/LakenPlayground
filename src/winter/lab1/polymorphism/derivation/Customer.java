package winter.lab1.polymorphism.derivation;


import winter.lab1.interfacesinclassb.Widget;
import winter.lab1.polymorphism.composition.BankAccount;

/**
 * @author Laken
 * @date 2026-01-27
 * @description
 */
public class Customer extends Person implements Comparable {
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
        String result = "";
//        getFirstName()
        return result;
    }


    /**
     *
     * @return the details of the customer for display
     */
    @Override
    public String toString() {
        return getFirstName() + ", " + getMiddleInit() + ", " + getLastName() + ".";
    }


    public int compareTo(Object o) {
        if (o instanceof Customer customer) {
            if (this.customerId > customer.customerId) {
                return 1;
            } else if (this.customerId < customer.customerId) {
                return -1;
            } else {
                return 0;
            }
        }
        else {
            return 0;
        }
    }
}
