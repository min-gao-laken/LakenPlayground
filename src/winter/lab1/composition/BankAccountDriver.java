package winter.lab1.composition;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2026-01-14
 * @description
 */

public class BankAccountDriver {
    public Scanner input;
    public BankAccount[] accounts;

    // constructor
    public BankAccountDriver() {
        input = new Scanner(System.in);
    }

    /**
     * generate an array to store information for 5 accounts
     */
    public void getData() {
        accounts = new BankAccount[5];
        accounts[0] = new BankAccount(1001, "Danny", "Vito", 'D', 3200.50f, new Date(7, 9, 2023));
        accounts[1] = new BankAccount(1002, "Eddi", "Ewhat", 'E', 4200.50f, new Date(10, 9, 2022));
        accounts[2] = new BankAccount(1003, "Fendy", "Fwhat", 'F', 5200.50f, new Date(8, 9, 2021));
        accounts[3] = new BankAccount(1004, "Geo", "Ewhat", 'G', 6200.50f, new Date(20, 9, 2020));
        accounts[4] = new BankAccount(1005, "Halo", "Hwhat", 'H', 7200.50f, new Date(9, 9, 2019));
//        System.out.println(accounts[0].getLastTransaction()); // to call toString()
    }

    /**
     * display the menu options
     *
     * @return an integer
     */
    public int showMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Display all accounts");
        System.out.println("2. Display info for account by account number");
        System.out.println("3. Edit information for account by account number");
        System.out.println("4. Deposit into account by account number");
        System.out.println("5. Withdraw from account by account number");
        System.out.println("6. Transfer from one account to another");
        System.out.println("7. Exit");
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
            case 6:
                menuOption6();
                break;
            case 7:
                System.out.println("Exit.");
                break;
            default:
                System.out.println("Invalid choice, try again!");
                break;
        }

    }

    /**
     * option 1: display all account information
     */
    public void menuOption1() {
        System.out.println("You have chosen option 1");
        for (BankAccount account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Owner: " + account.getFirstName() + " " + account.getMiddleInit() + " " + account.getLastName());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Last Transaction: " + account.getLastTransaction());
            System.out.println("----");
        }
    }

    /**
     * option 2: get target account information by index
     */
    public void menuOption2() {
        System.out.println("You have chosen option 2");
        System.out.print("Enter index: ");
        int index = input.nextInt();
        input.nextLine();
        while (index < 0 || index >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            index = input.nextInt();
            input.nextLine();
        }

        BankAccount account = accounts[index];
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Owner: " + account.getFirstName() + " " + account.getMiddleInit() + " " + account.getLastName());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Last Transaction: " + account.getLastTransaction());
    }

    /**
     * option 3: modify target account information by index
     * <p>
     * step 1: get target account
     * step 2: use a switch statement to select the corresponding option
     * step 3: enter the new information
     */
    public void menuOption3() {
        System.out.println("You have chosen option 3");
        System.out.print("Enter index: ");
        int index = input.nextInt();
        input.nextLine();
        while (index < 0 || index >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            index = input.nextInt();
            input.nextLine();
        }

        BankAccount account = accounts[index];
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Middle Initial");
        System.out.println("4. Cancel");
        System.out.print("Choice: ");
        int field = input.nextInt();
        input.nextLine();

        switch (field) {
            case 1:
                System.out.print("Enter new first name: ");
                account.setFirstName(input.nextLine());
                break;
            case 2:
                System.out.print("Enter new last name: ");
                account.setLastName(input.nextLine());
                break;
            case 3:
                System.out.print("Enter new middle initial: ");
                account.setMiddleInit(input.next().charAt(0));
                input.nextLine();
                break;
            case 4:
                System.out.println("Cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * option 4: call the deposit function
     */
    public void menuOption4() {
        System.out.println("You have chosen option 4");
        // Enter account id and prompt user how much they’d like to deposit as well as enter the last transaction date
        System.out.print("Enter index: ");
        int index = input.nextInt();
        input.nextLine();
        while (index < 0 || index >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            index = input.nextInt();
            input.nextLine();
        }

        BankAccount account = accounts[index];

        System.out.print("Enter deposit amount: ");


        boolean isValidValue = false;
        float amount = 0;
        /**
         * validate the amount value, amount should not be less than 0
         */
        while (!isValidValue) {
            amount = input.nextFloat();
            input.nextLine();
            if (amount >= 0) {
                isValidValue = true;
            } else {
                System.out.println("Invalid value, try again");

            }
        }


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

        Date date = new Date(day, month, year);
        account.deposit(amount, date);
    }

    /**
     * option 5: call the withdraw function
     */
    public void menuOption5() {
        System.out.println("You have chosen option 5");
//        Enter account id and prompt user how much they’d like to withdraw as well as enter the  last transaction date
        System.out.print("Enter index: ");
        int index = input.nextInt();
        input.nextLine();
        while (index < 0 || index >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            index = input.nextInt();
            input.nextLine();
        }

        BankAccount account = accounts[index];

//        System.out.print("Enter withdraw amount: ");
//        float amount = input.nextFloat();
//        input.nextLine();
        float amount = 0;
        /**
         * validate the amount value for withdraw function: amount > 0 && amount <= balance
         */
        do {
            System.out.print("Enter withdraw amount: ");
            amount = input.nextFloat();
            input.nextLine();

            if (amount <= 0) {
                System.out.println("Invalid value, try again");
            } else if (amount > account.getBalance()) {
                System.out.println("Sorry, insufficient balance");
            }
        } while (amount <= 0 || amount > account.getBalance());

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

        Date date = new Date(day, month, year);
        account.withdraw(amount, date);
    }

    /**
     * option 6: call the transfer function
     */
    public void menuOption6() {
        System.out.println("You have chosen option 6");
        System.out.print("Enter sender index: ");
        int indexOfSender = input.nextInt();
        input.nextLine();
        while (indexOfSender < 0 || indexOfSender >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            indexOfSender = input.nextInt();
            input.nextLine();
        }
        BankAccount sender = accounts[indexOfSender];

        System.out.print("Enter receiver index: ");
        int indexOfReceiver = input.nextInt();
        input.nextLine();
        while (indexOfReceiver < 0 || indexOfReceiver >= accounts.length) {
            System.out.println("Invalid input, try another index again");
            indexOfReceiver = input.nextInt();
            input.nextLine();
        }
        BankAccount receiver = accounts[indexOfReceiver];

//        System.out.print("Enter transfer amount: ");
//        float amount = input.nextFloat();
//        input.nextLine();

        /**
         * validate the amount for transfer function: amount > 0 && amount <= balance
         * the balance is updated in the sender's account.
         */
        float amount = 0;
        do {
            System.out.print("Enter transfer amount: ");
            amount = input.nextFloat();
            input.nextLine();

            if (amount <= 0 || amount > sender.getBalance()) {
                System.out.println("Invalid value, try again");
            }
        } while (amount <= 0 || amount > sender.getBalance());

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

        Date date = new Date(day, month, year);

        // transfer(float amount, BankAccount receiver, String date)
        sender.transfer(amount, receiver, date);
    }

    public static void main(String[] args) {
        BankAccountDriver driver = new BankAccountDriver();
        driver.getData();
        int choice;
        do {
            choice = driver.showMenu();
            driver.executeChoice(choice);
        } while (choice != 7);
    }
}
