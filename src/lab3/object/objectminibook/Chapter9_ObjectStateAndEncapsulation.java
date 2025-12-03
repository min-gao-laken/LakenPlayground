package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-02
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class called Wallet with a private balance.
 * 2. Provide methods:
 * - addMoney(double amount)
 * - removeMoney(double amount)
 * - getBalance()
 * 3. Ensure removeMoney(...) does not allow the balance to
 * become negative.
 * 4. Create a Wallet object and test all behaviours.
 * ------------------------------------------------------------
 */
public class Chapter9_ObjectStateAndEncapsulation {
    static class Wallet {
        private double balance;

        public void addMoney(double amount) {
            balance += amount;
        }

        public void removeMoney(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
            } else {
                System.out.println("Try again");
            }
        }

        public double getBalance() {
            return this.balance;
        }
    }

    public static void main(String[] args) {
        Wallet wallet = new Wallet();
        wallet.addMoney(100);
        System.out.println(wallet.getBalance());
        wallet.removeMoney(200);
        System.out.println(wallet.getBalance());
        wallet.removeMoney(50);
        System.out.println(wallet.getBalance());
    }
}
