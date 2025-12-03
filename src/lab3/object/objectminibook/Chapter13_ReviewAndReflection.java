package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-03
 * @description TRY THIS (FINAL EXERCISE)
 * ------------------------------------------------------------
 * 1. Design a class BankCard with private fields:
 * - ownerName
 * - balance
 * 2. Add:
 * - a constructor
 * - deposit and withdraw methods
 * - getters for both fields
 * 3. Create several BankCard objects and compare their balances.
 * 4. Explain in your own words how encapsulation protects the
 * BankCard from invalid values.
 * ------------------------------------------------------------
 */
public class Chapter13_ReviewAndReflection {
    static class BankCard {
        private String ownerName;
        private double balance;

        public BankCard(String ownerName, double balance) {
            if (balance > 0) {
                this.balance = balance;
            } else {
                this.balance = 0;
            }
            this.ownerName = ownerName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) balance += amount;
        }

        public void withdraw(double amount) {
            if (balance - amount >= 0 && amount >= 0) {
                balance -= amount;
            }
        }
    }

    public static void main(String[] args) {
        BankCard bc1 = new BankCard("RichPerson", 500);
        BankCard bc2 = new BankCard("SecondRichPerson", 300);

        System.out.println(bc1.getOwnerName() + " has: " + bc1.getBalance());
        System.out.println(bc2.getOwnerName() + " has: " + bc2.getBalance());

        bc1.deposit(200);
        bc2.withdraw(200);

        System.out.println("Something happened: ");
        System.out.println(bc1.getOwnerName() + " has: " + bc1.getBalance());
        System.out.println(bc2.getOwnerName() + " has: " + bc2.getBalance());
    }
}

// Explain:
//The fields of BankCard are private, so they cannot be changed directly from outside.
//The balance can only be changed through deposit or withdraw methods,
//which check for invalid values, keeping the object in a valid state.