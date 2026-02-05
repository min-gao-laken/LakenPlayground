package winter.lab1.polymorphism;

import winter.lab1.polymorphism.composition.BankAccount;
import winter.lab1.polymorphism.composition.Date;
import winter.lab1.polymorphism.derivation.Customer;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2026-02-04
 * @description You will then create a new driver called BankAccountReporter that
 * manipulates an array that contains both ChequingAccount and
 * SavingsAccount objects
 */
public class BankAccountReporter {
    static Customer[] customers;

    public Scanner input;
    public BankAccount[] accounts;


    // constructor
    public BankAccountReporter() {
        input = new Scanner(System.in);
    }

    /**
     * populate the accounts and customers arrays with data. Notice that we are
     * putting ChequingAccount and SavingsAccount into the same array of type
     * BankAccount
     */
    public void getData() {
        customers = new Customer[10];
        customers[0] = new Customer(005, "Danny", "Vito", 'D');
        customers[1] = new Customer(004, "Amy", "Santiago", 'B');
        customers[2] = new Customer(003, "Marjorie", "Simpson", 'J');
        customers[3] = new Customer(001, "Tetsuo", "Shima", 'A');
        customers[4] = new Customer(002, "Jonas", "Khanwald", 'M');
        customers[5] = new Customer(006, "Pedro", "Pascal", 'B');
        customers[6] = new Customer(007, "Kaitlyn", "Olsen", 'D');

        accounts = new BankAccount[10];
        accounts[0] = new ChequingAccount(1005, customers[2], 76.57f, new
                Date(2019, 11, 5), 10.50f);
        accounts[1] = new SavingsAccount(1001, customers[0], 6500.50f, new
                Date(2023, 9, 7), 10);
        accounts[2] = new SavingsAccount(1003, customers[2], 5533.57f, new
                Date(2019, 12, 6), 1.0f);
        accounts[3] = new SavingsAccount(1006, customers[4], 0.60f, new
                Date(2020, 7, 7), 10.0f);
        accounts[4] = new ChequingAccount(1002, customers[1], 2576.57f, new
                Date(2022, 9, 8), 10.50f);
        accounts[5] = new ChequingAccount(1008, customers[0], 200.10f, new
                Date(2023, 12, 8), 10.0f);
        accounts[6] = new SavingsAccount(1009, customers[2], 33.21f, new
                Date(2021, 5, 6), 1.0f);
        accounts[7] = new ChequingAccount(1010, customers[5], 500000.97f, new
                Date(2020, 11, 1), 100.0f);
        accounts[8] = new SavingsAccount(1004, customers[3], 3000.60f, new
                Date(2017, 10, 3), 10.0f);
        accounts[9] = new ChequingAccount(1007, customers[6], 9000.50f, new
                Date(2022, 11, 3), 50f);
    }

    /**
     * display the menu options
     *
     * @return an integer
     */
    public int showMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Display all accounts");
        System.out.println("2. Display all customers");
        System.out.println("3. Display all customers for a customer");
        System.out.println("4. Calculate Interes");
        System.out.println("5. Withdraw from account by account number");
        System.out.println("999. Exit");
        System.out.print("Choice: ");

        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    /**
     *
     * @param choice: accept the choice and use a switch statement to select the corresponding option
     */
    public void executeChoice(int choice) {
//        This method accepts an integer and a switch statement to handle the values of choice.
        switch (choice) {
            case 1:
                menuOption1();
                break;
            case 2:
                menuOption2();
                break;
            case 3:
                menuOption3();
                break;
            case 4:
                menuOption4();
                break;
            case 5:
                menuOption5();
                break;
            case 999:
                System.out.println("Exit.");
                break;
            default:
                System.out.println("Invalid choice, try again!");
                break;
        }

    }

    /**
     * option 1: display all account information in the following format:
     * AccountType# AccountNumber: customerId, lastName, firstName middleInit balance
     * <p>
     * Finish the list with a sum of all balances
     *
     */
    public void menuOption1() {
        System.out.println("You have chosen option 1");
        System.out.println("What type of account do you want to check?");

        System.out.println("1. Chequing Accounts");
        System.out.println("2. Savings Accounts");
        System.out.println("3. All accounts");

        int subOption = input.nextInt();
        input.nextLine();
        while (subOption <= 0 || subOption > 3) {
            System.out.println("Invalid input, try again");
            subOption = input.nextInt();
            input.nextLine();
        }
        float sum = 0;
        switch (subOption) {
            case 1:
                System.out.println("1. Chequing Accounts");
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] instanceof ChequingAccount) {
                        ChequingAccount c1 = (ChequingAccount) accounts[i];
                        System.out.println(c1.accountType());
                        System.out.println("Account Number: " + c1.getAccountNumber());
                        System.out.println("Customer ID: " + c1.getCustomer().getCustomerId());
                        System.out.println("Account Owner: "
                                + c1.getCustomer().getLastName()
                                + " " + c1.getCustomer().getFirstName()
                                + " " + c1.getCustomer().getMiddleInit()
                        );
                        System.out.println("Balance: " + c1.getBalance());
                        System.out.println("----");
                        sum += c1.getBalance();
                    }
                }
                System.out.println("a sum of all balances: " + sum);
                break;
            case 2:
                System.out.println("2. Savings Accounts");
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] instanceof SavingsAccount) {
                        SavingsAccount s1 = (SavingsAccount) accounts[i];
                        System.out.println(s1.accountType());
                        System.out.println("Account Number: " + s1.getAccountNumber());
                        System.out.println("Customer ID: " + s1.getCustomer().getCustomerId());
                        System.out.println("Account Owner: "
                                + s1.getCustomer().getLastName()
                                + " " + s1.getCustomer().getFirstName()
                                + " " + s1.getCustomer().getMiddleInit()
                        );
                        System.out.println("Balance: " + s1.getBalance());
                        System.out.println("----");
                        sum += s1.getBalance();
                    }
                }
                System.out.println("a sum of all balances: " + sum);
                break;
            case 3:
                System.out.println("3. All accounts");
                for (BankAccount account : accounts) {
                    System.out.println(account.accountType());
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Customer ID: " + account.getCustomer().getCustomerId());
                    System.out.println("Account Owner: "
                            + account.getCustomer().getLastName()
                            + " " + account.getCustomer().getFirstName()
                            + " " + account.getCustomer().getMiddleInit()
                    );
                    System.out.println("Balance: " + account.getBalance());
                    System.out.println("----");
                    sum += account.getBalance();
                }
                System.out.println("a sum of all balances: " + sum);
                break;
            default:
                System.out.println("Invalid choice.");
        }

    }


    /**
     * option 2: Display all customers information
     */
    public void menuOption2() {
        for (int i = 0; i < 7; i++) {
            System.out.println("00" + customers[i].getCustomerId() + ": "
                    + customers[i].getLastName() + ' '
                    + customers[i].getFirstName() + ' '
                    + customers[i].getMiddleInit()
            );
        }
    }

    /**
     * option 3: enter a customer number and the program will display all
     * the accounts that have that customer
     */
    public void menuOption3() {
        System.out.println("Enter a customer number");
        int customerID = input.nextInt();
        input.nextLine();

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getCustomer().getCustomerId() == customerID) {
                System.out.println(accounts[i].accountType());
                System.out.println("Account Number: " + accounts[i].getAccountNumber());
                System.out.println("Customer ID: " + accounts[i].getCustomer().getCustomerId());
                System.out.println("Account Owner: " + accounts[i].getCustomer().getFirstName()
                        + " " + accounts[i].getCustomer().getMiddleInit()
                        + " " + accounts[i].getCustomer().getLastName()
                );
                System.out.println("Balance: " + accounts[i].getBalance());
                System.out.println("Last Transaction: " + accounts[i].getLastTransaction());
                System.out.println("----");
            }
        }
    }

    /**
     * enter an account number and, if the account is a savings
     * account, a number of months. The application should then call the accrue
     * interest function to increase the balance of the account
     */
    public void menuOption4() {
        System.out.println("Enter an account number");
        int accountID = input.nextInt();
        input.nextLine();

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountNumber() == accountID) {
                if (accounts[i] instanceof SavingsAccount) {
                    System.out.println("This is a saving account! You got some interest! Please check your account information below");
                    SavingsAccount s1 = (SavingsAccount) accounts[i];
                    System.out.println(s1.accountType());
                    System.out.println("Account Number: " + s1.getAccountNumber());
                    System.out.println("Customer ID: " + s1.getCustomer().getCustomerId());
                    System.out.println("Account Owner: "
                            + s1.getCustomer().getLastName()
                            + " " + s1.getCustomer().getFirstName()
                            + " " + s1.getCustomer().getMiddleInit()
                    );
                    System.out.println("Balance: " + s1.getBalance());

                    // call the accrue interest function to increase the balance of the account
                    float interest = ((SavingsAccount) accounts[i]).accrueInterest(3, accounts[i].getBalance()); // set the month value as 3
                    System.out.println("The interest is: " + interest);
                    System.out.print("Enter the transaction date: Year: ");
                    int year = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter the transaction date: Month: ");
                    int month = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter the transaction date: Day: ");
                    int day = input.nextInt();
                    input.nextLine();

                    // call the deposit function to add money
                    Date date = new Date(year, month, day);
                    accounts[i].deposit(interest, date);
                }
            }
        }
    }

    /**
     * enter an account number, and then call the withdraw function
     * if chequingAccount,
     * if ChequingAccount,
     */
    public void menuOption5() {
        System.out.println("Enter an account number");
        int accountID = input.nextInt();
        input.nextLine();

        /**
         * Change the date edit functionality so it asks for month, day and year separately
         * (i.e. you the user is prompted for, and enters day, month year in sequence).
         */

        System.out.print("Enter last transaction date: Day: ");
        int day = input.nextInt();
        input.nextLine();

        System.out.print("Enter last transaction date: Month: ");
        int month = input.nextInt();
        input.nextLine();

        System.out.print("Enter last transaction date: Year: ");
        int year = input.nextInt();
        input.nextLine();

        Date date = new Date(year, month, day);

        System.out.print("Enter withdraw amount: ");
        float amount = input.nextFloat();
        input.nextLine();

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountNumber() == accountID) {
                accounts[i].withdraw(amount, date);
            }
        }
    }


    public static void main(String[] args) {
        BankAccountReporter driver = new BankAccountReporter();
        driver.getData();
        int choice;
        do {
            choice = driver.showMenu();
            driver.executeChoice(choice);
        } while (choice != 999);
    }
}
