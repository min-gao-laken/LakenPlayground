package winter.lab1.polymorphism;

import winter.lab1.polymorphism.composition.BankAccount;
import winter.lab1.polymorphism.composition.Date;
import winter.lab1.polymorphism.derivation.Customer;

/**
 * @author Laken
 * @date 2026-02-04
 * @description For every month
 * the savings account accrues interest we multiply the balance by the interest
 * rate ($100 accruing at 10% over 3 months would be 100 * 1.1 * 1.1 * 1.1 = $133.10)
 */
public class SavingsAccount extends BankAccount {
    private float interestRate;

    public SavingsAccount() {
    }

    public SavingsAccount(int accountNumber, String firstName, String lastName, char middleInit, float balance, Date lastTransaction) {
        super(accountNumber, firstName, lastName, middleInit, balance, lastTransaction);
    }

    public SavingsAccount(int accountNumber, Customer customer, float balance, Date lastTransaction) {
        super(accountNumber, customer, balance, lastTransaction);
    }

    public SavingsAccount(int accountNumber, Customer customer, float balance, Date lastTransaction, float interestRate) {
        super(accountNumber, customer, balance, lastTransaction);
        this.interestRate = interestRate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public float accrueInterest(int months, float balance) {
        interestRate = interestRate / 100; // because I think the provided value is too large
//        System.out.println(interestRate);
        float balanceCopy = balance;
        for (int i = 0; i < months; i++) {
            balance *= (1 + interestRate); // 100 * 1.1 * 1.1 * 1.1 = $133.10
            System.out.println(balance);
        }
        return balance - balanceCopy;
    }

    public void withdraw(float amount, Date lastTransaction) {
        if (amount > 0 && amount <= getBalance()) { // todo: the way to call the balance!
//            balance -= amount;
            setBalance(getBalance() - amount);
//            this.lastTransaction = lastTransaction;
            setLastTransaction(lastTransaction);
            System.out.println("Withdraw successful. New balance: " + getBalance());
        } else {
            System.out.println("Invalid value.");
        }
    }

    @Override
    public String accountType() {
        return "SavingsAccount#";
    }
}