package lab3.object.assignment3;

/**
 * @author Laken
 * @date 2025-12-04
 * @description
 */
public class Account {
    private int accountId;
    private double balance;
    private int accountNumber;
    private double interestRate;

    private static int numberOfAccounts = 0;

    public Account(double openingBalance, int accountNumber, double interestRate) {
        if (openingBalance < 0) {
            System.out.println("Opening balance cannot be negative. Setting to 0.");
            this.setBalance(0);
        } else {
            this.setBalance(openingBalance);
        }

        numberOfAccounts++;
        this.accountId = numberOfAccounts;

        this.setAccountNumber(accountNumber);
        this.setInterestRate(interestRate);
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public static int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Deposit amount must be at least 0.");
        } else {
            balance += amount;
            System.out.println("$" + amount + " deposited. New balance: $" + balance);
        }
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount must be at least 0.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal canceled.");
        } else {
            balance -= amount;
            System.out.println("$" + amount + " withdrawn. New balance: $" + balance);
        }
    }

    public void transfer(Account targetAccount, double amount) {
        if (amount < 0) {
            System.out.println("Transfer amount must be at least 0.");
        } else if (amount > this.balance) {
            System.out.println("Insufficient funds. Transfer canceled.");
        } else {
            this.withdraw(amount);
            targetAccount.deposit(amount);
            System.out.println("$" + amount + " transferred to account " + targetAccount.getAccountId());
        }
    }

    public static void main(String[] args) {
        System.out.println("Number of accounts at start: " + Account.getNumberOfAccounts());

        Account acc1 = new Account(500.0, 12345, 0.03);
        System.out.println("Account 1 created with balance: $" + acc1.getBalance());

        acc1.deposit(200);
        acc1.deposit(-50);

        acc1.withdraw(100);
        acc1.withdraw(1000);
        acc1.withdraw(-20);

        Account acc2 = new Account(300.0, 67890, 0.04);
        System.out.println("Account 2 created with balance: $" + acc2.getBalance());

        acc1.transfer(acc2, 100);
        acc1.transfer(acc2, 1000);
        acc1.transfer(acc2, -50);

        System.out.println("Account 1 balance after transfer: $" + acc1.getBalance());
        System.out.println("Account 2 balance after transfer: $" + acc2.getBalance());

        System.out.println("Number of accounts at end: " + Account.getNumberOfAccounts());
    }
}
