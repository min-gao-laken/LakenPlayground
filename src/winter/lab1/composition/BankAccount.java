package winter.lab1.composition;

/**
 * @author Laken
 * @date 2026-01-14
 * @description
 */
public class BankAccount {
    private int accountNumber;
    private String firstName;
    private String lastName;
    private char middleInit;
    private float balance;
    private Date lastTransaction;

    public BankAccount() {
    }

    public BankAccount(int accountNumber, String firstName, String lastName, char middleInit, float balance, Date lastTransaction) {
        setAccountNumber(accountNumber);
        setFirstName(firstName);
        setLastName(lastName);
        setMiddleInit(middleInit);
        setBalance(balance);
        setLastTransaction(lastTransaction.day, lastTransaction.month, lastTransaction.year);
    }

    // getters and setters
    public int getAccountNumber() {
        return accountNumber;
    }

    // Non-negative accountNumber only
    public void setAccountNumber(int accountNumber) {
        if (accountNumber >= 0) {
            this.accountNumber = accountNumber;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getMiddleInit() {
        return middleInit;
    }

    public void setMiddleInit(char middleInit) {
        this.middleInit = middleInit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(int day, int month, int year) {
        this.lastTransaction = new Date(day, month, year);
    }

    /**
     * When a deposit is made, the current balance should be added by the amount.
     * and we the current transaction time should be changed.
     *
     * @param amount
     * @param lastTransaction
     */
    public void deposit(float amount, Date lastTransaction) {
        if (amount > 0) {
            balance += amount;
            this.lastTransaction = lastTransaction;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    /**
     * When a withdrawal is made, the current balance should be decreased by the amount.
     * And we the current transaction time should be changed.
     * And most importantly, the amount should not be less than balance
     *
     * @param amount
     * @param lastTransaction
     */
    public void withdraw(float amount, Date lastTransaction) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            this.lastTransaction = lastTransaction;
            System.out.println("Withdraw successful. New balance: " + balance);
        } else {
            System.out.println("Invalid value.");
        }
    }

    /**
     * transfer should use withdraw and deposit functions
     *
     * @param amount
     * @param receiver
     * @param lastTransaction
     */
    public void transfer(float amount, BankAccount receiver, Date lastTransaction) {
        if (amount > 0 && amount <= balance) {
//            update the same transaction date for both receiver and sender
            this.withdraw(amount, lastTransaction);
            receiver.deposit(amount, lastTransaction); // The balance is on the sender.
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Invalid value.");
        }
    }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }
//    public static void main(String[] args) {
//
//    }
}
