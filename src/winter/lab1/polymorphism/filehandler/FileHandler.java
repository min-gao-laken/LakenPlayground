package winter.lab1.polymorphism.filehandler;


import winter.lab1.polymorphism.ChequingAccount;
import winter.lab1.polymorphism.composition.BankAccount;
import winter.lab1.polymorphism.composition.Date;
import winter.lab1.polymorphism.derivation.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Laken
 * @date 2026-01-20
 * @description
 */
public class FileHandler {
    private static BankAccount[] accounts;
    private static Scanner input;

    /**
     * Read a data file populate an array with its contents
     *
     * @param fileName the name of the file (including the extension)
     * @return BankAccount array with contents of file
     */
    public static BankAccount[] getData(String fileName) {
        try {
            input = new Scanner(new File(fileName));
            int numAccounts = input.nextInt();
//            System.out.println("Number of accounts: " + numAccounts);
            accounts = new BankAccount[numAccounts];

            for (int i = 0; i < numAccounts; i++) {
                // read the data from the file
                int accountNum = input.nextInt();
//                String firstName = input.next();
//                String lastName = input.next();
//                char init = input.next().charAt(0);
                float balance = input.nextFloat();

                Date transactionDate = new Date(input.nextInt(), //day
                        input.nextInt(),// month
                        input.nextInt()); // year

                Customer customer = new Customer(input.nextInt(), // customer ID
                        input.next(), // first name
                        input.next(), // last name
                        input.next().charAt(0) // middle init
                );

                accounts[i] = new ChequingAccount(accountNum, customer, balance, transactionDate);

//                accounts[i] = new BankAccount(accountNum, firstName, lastName, init, balance, transactionDate);
            }
            // crete an account

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("An unknow error occurred");
            e.printStackTrace();
        }

        return accounts;
    }

    /**
     * Write the data of BankAccounts to a file
     *
     * @param accountsToSave - an array of bank accounts to populate
     *                       the text file
     */
    public static void save(BankAccount[] accountsToSave) {

        try {
            // create a file writer object
            // string - file name
            // boolean - whether to append the new data or overwrite the existing file
            FileWriter fw = new FileWriter("233BankAccountTestData.txt", false);

            // write the number of accounts
            // convert the number of accounts to a string to prevent issues whe write to file
            String numAccounts = String.valueOf(accountsToSave.length);
//            fw.write(accountsToSave.length);
            fw.write(numAccounts);
            fw.write(System.lineSeparator());

            // write the contents of the array to the file
            for (int i = 0; i < accountsToSave.length; i++) {
                fw.write(accountsToSave[i].writeAsRecord());
                fw.write(System.lineSeparator());
            }

            // save the file
            // close the file
            fw.close();

        } catch (IOException ioe) {
            System.out.println("Unknown IO Exception occurred");
            ioe.printStackTrace(); // printStackTrace() is used to print the full exception stack trace to the console.
        }
    }

//    public static void main(String[] args) {
//        getData("thisshoulderror");
//
//        getData("./233BankAccountTestData.txt");
//        for (int i = 0; i < accounts.length; i++) {
//            System.out.println(accounts[i].getLastName() + ", " + accounts[i].getFirstName());
//        }

//        getData("233BankAccountTestData.txt");
//        accounts[0].setFirstName("TESTING2");
//        save(accounts);
//
//
//    }
}