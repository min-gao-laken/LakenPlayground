package winter.lab1.bankaccountprogram;

/**
 * @author Laken
 * @date 2026-01-06
 * @description
 */
public class BankAccount {
    private int accountNumber;
    private String firstName;
    private String lastName;
    private char middleInit;
    private float balance;
    private String lastTransaction; //date string

    public BankAccount() {
    }

    // Make sure the parameters are passed correctly.
    public BankAccount(int accountNumber, String firstName, String lastName, char middleInit,
                       float balance, String lastTransaction) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInit = middleInit;
        this.balance = balance;
        this.lastTransaction = lastTransaction;
    }


    // getter
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getMiddleInit() {
        return middleInit;
    }

    public float getBalance() {
        return balance;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }

    // setter
    // Non-negative accountNumber only
    public void setAccountNumber(int accountNumber) {
        if (accountNumber < 0) {
            throw new IllegalArgumentException("Account number must be non-negative.");
        } else {
            this.accountNumber = accountNumber;
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleInit(char middleInit) {
        this.middleInit = middleInit;
    }

//    public void setBalance(float balance) {
//        this.balance = balance;
//    }
//
//    public void setLastTransaction(String lastTransaction) {
//        this.lastTransaction = lastTransaction;
//    }

    public void deposit(float amt, String date) {
        // balance=balance+amount
        // /and set new lastTransaction date
        // /only set positive amounts
        if (amt > 0) {
            balance += amt;
            lastTransaction = date;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void withdraw(float amt, String date) {
        if (amt > 0 && amt <= balance) {
            balance -= amt;
            lastTransaction = date;
            System.out.println("Withdraw successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    public void transfer(float amt, BankAccount receiver, String date) {
        if (amt > 0 && amt <= balance) {
            this.balance -= amt; // Tip: The balance is on the sender.
            receiver.balance += amt;
            this.lastTransaction = date;
            receiver.lastTransaction = date;

            System.out.println("Transfer successful. Your new balance: " + this.balance);
//            No need to know the receiver's balance in this function, as it is called by the sender.
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }
//    There is no need for a main method here.
//    public static void main(String[] args) {
//
//    }
}
