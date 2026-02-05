package winter.lab1.polymorphism.composition;


import winter.lab1.polymorphism.derivation.Customer;

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
    private Customer customer;

    public BankAccount() {
    }

    public BankAccount(int accountNumber, String firstName, String lastName, char middleInit, float balance, Date lastTransaction) {
        setAccountNumber(accountNumber);
        setFirstName(firstName);
        setLastName(lastName);
        setMiddleInit(middleInit);
        setBalance(balance);
        setLastTransaction(lastTransaction.year, lastTransaction.month, lastTransaction.day);
    }

    public BankAccount(int accountNumber, Customer customer, float balance, Date lastTransaction) {
        setAccountNumber(accountNumber);

//        setFirstName(customer.getFirstName());
//        setLastName(customer.getLastName());
//        setMiddleInit(customer.getMiddleInit());

        setCustomer(customer);
        setBalance(balance);
        setLastTransaction(lastTransaction.year, lastTransaction.month, lastTransaction.day);
    }

    public String writeAsRecord() {
        String result = "";
//        format: 1001 3200.5 2023 9 7 10011 TESTING2 Vito D
        result = accountNumber + " " +
                balance + " " +
                lastTransaction.getYear() + " " +
                lastTransaction.getMonth() + " " +
                lastTransaction.getDay() + " " +
                customer.getCustomerId() + " " +
                customer.getFirstName() + " " +
                customer.getMiddleInit() + " " +
                customer.getLastName();
        return result;
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

    public void setLastTransaction(int year, int month, int day) {
        this.lastTransaction = new Date(year, month, day);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

}
