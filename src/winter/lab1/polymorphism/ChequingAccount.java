package winter.lab1.polymorphism;

import winter.lab1.polymorphism.composition.BankAccount;
import winter.lab1.polymorphism.composition.Date;
import winter.lab1.polymorphism.derivation.Customer;

/**
 * @author Laken
 * @date 2026-02-04
 * @description A ChequingAccount is a type of bank
 * account for everyday spending that allows for overdrafting, which is the
 * ability to go into the negatives up to their overDraftLimit
 */
public class ChequingAccount extends BankAccount {
    private float overDraftLimit;

    public ChequingAccount() {
    }

    public ChequingAccount(int accountNumber, String firstName, String lastName, char middleInit, float balance, Date lastTransaction) {
        super(accountNumber, firstName, lastName, middleInit, balance, lastTransaction);
    }

    public ChequingAccount(int accountNumber, Customer customer, float balance, Date lastTransaction) {
        super(accountNumber, customer, balance, lastTransaction);
    }

    public ChequingAccount(int accountNumber, Customer customer, float balance, Date lastTransaction, float overDraftLimit) {
        super(accountNumber, customer, balance, lastTransaction);
        this.overDraftLimit = overDraftLimit;
    }

    public float getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(float overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    public void withdraw(float amount, Date lastTransaction) {
        float availableMoney = getBalance() + overDraftLimit;
//        System.out.println(getBalance() + "___" + overDraftLimit+ "____"+(getBalance() - amount));
        if (amount > 0 && amount <= availableMoney) {
            setBalance(getBalance() - amount);
            setLastTransaction(lastTransaction);
            System.out.println("Withdraw successful. New balance: " + getBalance());
        } else {
            System.out.println("Invalid value.");
        }
    }

    @Override
    public String accountType() {
        return "ChequingAccount#";
    }
}


//Customer has bank account, bank account does not have a customer (-3)
