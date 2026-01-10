package winter.lab1.bankaccountprogram;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2026-01-06
 * @description
 */
public class BankAccountDriver {
    public Scanner input;
    public BankAccount[] accounts;

    // constructor
    public BankAccountDriver() {
        input = new Scanner(System.in);
    }

    public void getData() {
//        accounts[0] = new BankAccount(1001, "Danny", "Vito", 'D', 3200.50f,
//                "07/09/23");
//        accounts[1] = new BankAccount(
//                1002, "Luke", "L", 'L', 100.33, "01/06/2028"
//        );
        accounts = new BankAccount[]{
                new BankAccount(1001, "Danny", "Vito", 'D', 100.50f, "07/09/23"),
                new BankAccount(1002, "Luke", "L", 'L', 100.33f, "01/06/2028"),
                new BankAccount(1003, "Anna", "Smith", 'A', 100.12f, "12/12/2027"),
                new BankAccount(1004, "Bob", "Brown", 'B', 100.2f, "03/03/2028"),
                new BankAccount(1005, "Cathy", "Lee", 'C', 100.5f, "05/05/2028")
        };
    }

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
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice, try again!");
                break;
        }

    }

    public void menuOption1() {
        for (BankAccount account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Owner: " + account.getFirstName() + " " + account.getMiddleInit() + " " + account.getLastName());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Last Transaction: " + account.getLastTransaction());
            System.out.println("----");
        }
    }

    private BankAccount findAccount(int id) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == id) return acc;
        }
        return null; // We can assign null to any reference type variable (e.g., BankAccount acc = null;).
    }

    public void menuOption2() {
        System.out.print("Enter account number: ");
        int id = input.nextInt();
        BankAccount acc = findAccount(id);
        if (acc != null) System.out.println(acc.getLastName());
        else System.out.println("Account not found.");
    }

    public void menuOption3() {
        System.out.print("Enter account number to edit: ");
        int id = input.nextInt();
        input.nextLine();
        BankAccount acc = findAccount(id);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
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
                acc.setFirstName(input.nextLine());
                break;
            case 2:
                System.out.print("Enter new last name: ");
                acc.setLastName(input.nextLine());
                break;
            case 3:
                System.out.print("Enter new middle initial: ");
                acc.setMiddleInit(input.next().charAt(0));
                input.nextLine();
                break;
            case 4:
                System.out.println("Cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void menuOption4() {
        // Enter account id and prompt user how much they’d like to deposit as well as enter the last transaction date
        System.out.print("Enter account number: ");
        int id = input.nextInt();
        BankAccount acc = findAccount(id);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter deposit amount: ");
        float amt = input.nextFloat();
        input.nextLine();
        System.out.print("Enter last transaction date: ");
        String date = input.nextLine();
        acc.deposit(amt, date);
    }

    public void menuOption5() {
//        Enter account id and prompt user how much they’d like to withdraw as well as enter the  last transaction date
        System.out.print("Enter account number: ");
        int id = input.nextInt();
        BankAccount acc = findAccount(id);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter withdraw amount: ");
        float amt = input.nextFloat();
        input.nextLine();
        System.out.print("Enter last transaction date: ");
        String date = input.nextLine();
        acc.withdraw(amt, date);
    }

    public void menuOption6() {
//        Enter account id of sender and receiver and prompt user how much they’d like to transfer as well as enter the last transaction date.
        System.out.print("Enter sender account number: ");
        int id = input.nextInt();
        BankAccount sender = findAccount(id);
        if (sender == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter receiver account number: ");
        int receiverID = input.nextInt();
        BankAccount receiver = findAccount(receiverID);
        if (receiver == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter transfer amount: ");
        float amt = input.nextFloat();
        input.nextLine();
        System.out.print("Enter last transaction date: ");
        String date = input.nextLine();
//        transfer(float amt, BankAccount receiver, String date)
        sender.transfer(amt, receiver, date);
    }

    public static void main(String[] args) {
        BankAccountDriver driver = new BankAccountDriver();
        driver.getData();
        int choice;
        do {
            choice = driver.showMenu();
            driver.executeChoice(choice);
        } while (choice != 7);
        System.out.println("Program exited.");
    }
}