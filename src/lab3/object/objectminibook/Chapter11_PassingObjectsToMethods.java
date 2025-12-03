package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-02
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create two Account objects with different starting balances.
 * 2. Write a method transfer(Account from, Account to, double amt)
 * that:
 * - withdraws amt from the first account
 * - deposits amt into the second
 * - ignores invalid transfers
 * 3. Test several valid and invalid transfers.
 * ------------------------------------------------------------
 */
public class Chapter11_PassingObjectsToMethods {
    static class Account {
        private double balance;

        public Account(double initial) {
            if (initial >= 0) balance = initial;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) balance += amount;
        }

        public void withdraws(double amount) {
            if (balance >= amount) {
                balance = balance - amount;
            }
        }
    }

    public static void transfer(Account from, Account to, double amt) {
        if (from.getBalance() > amt) {
            to.deposit(amt);
            from.withdraws(amt);
        }
    }

    public static void main(String[] args) {
        Account a1 = new Account(500);
        Account a2 = new Account(100);
        System.out.println(a1.getBalance());
        System.out.println(a2.getBalance());

        System.out.println("Test 1:");
        transfer(a1, a2, 100);
        System.out.println(a1.getBalance());
        System.out.println(a2.getBalance());

        System.out.println("Test 2:");
        transfer(a1, a2, 500);
        System.out.println(a1.getBalance());
        System.out.println(a2.getBalance());
    }
}
