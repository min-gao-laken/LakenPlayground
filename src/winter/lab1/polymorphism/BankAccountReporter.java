package winter.lab1.polymorphism;

import winter.lab1.polymorphism.composition.BankAccount;

/**
 * @author Laken
 * @date 2026-02-04
 * @description You will then create a new driver called BankAccountReporter that
 * manipulates an array that contains both ChequingAccount and
 * SavingsAccount objects
 */
public class BankAccountReporter {
    public void manipulateAccount(BankAccount account) {
        if (account instanceof SavingsAccount) {
            System.out.println("Hi, you are SavingsAccount!");
        } else if (account instanceof ChequingAccount) {
            System.out.println("Hi, you are ChequingAccount!");
        }
    }
}