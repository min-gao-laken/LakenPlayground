package finalproject;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2025-12-06
 * @description Create a RentalAccount (user enters all information except movies and balance)
 *  Display all ra information.
 *  Allow editing of basic ra information.
 *  Display all rented movies.
 *  Allow renting a movie.
 *  Allow dropping a movie.
 */

public class RentalAccount {
    private static String storeName = "Laken's Final Project";
    // properties
    private int accountID;
    private String firstName;
    private String lastName;
    private double balanceOwed;
    private int[] movieIDs = new int[5];
    private String[] movieTitles = new String[5];

    // getter
    public int getAccountID() {
        return accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getBalanceOwed() {
        return balanceOwed;
    }

    public int[] getMovieIDs() {
        return movieIDs;
    }

    public String[] getMovieTitles() {
        return movieTitles;
    }

    // setter
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public RentalAccount() {
        for (int i = 0; i < 5; i++) {
            movieIDs[i] = 9999;
            movieTitles[i] = "NoMovie";
        }
    }

    private void setMovieIDByIndex(int index, int newMovieID) {
        if (index >= 0 && index < movieIDs.length) {
            movieIDs[index] = newMovieID;
        }
    }

    private void setMovieTitleByIndex(int index, String newMovieTitle) {
        if (index >= 0 && index < movieTitles.length) {
            movieTitles[index] = newMovieTitle;
        }
    }

    //+ getFullName(): String    // lastName, firstName
    public String getFullName() {
        return lastName + ", " + firstName;
    }

    //+ printRentedMovies(): void // movieID: movieTitle
    public void printRentedMovies() {
        for (int i = 0; i < movieIDs.length; i++) {
            System.out.println(movieIDs[i] + ": " + movieTitles[i]);
        }
    }

    //+ dropMovieByID(int idToDrop): void    // remove certain movie by ID
//    searches the movieIDs array
//    When the ID is found, it retrieves that index
//    sets movieIDs[index] to 9999
//    sets movieTitles[index] to “NoMovie” using private setters
    public void dropMovieByID(int idToDrop) {
        for (int i = 0; i < movieIDs.length; i++) {
            if (i == idToDrop) {
                setMovieIDByIndex(i, 9999);
                setMovieTitleByIndex(i, "NoMovie");
                updateBalance();
            }
        }
    }

    //+ rentMovie(int index, int movieID, String title): void
    public void rentMovie(int index, int movieID, String title) {
        if (index >= 0 && index < movieIDs.length) {
            setMovieIDByIndex(index, movieID);
            setMovieTitleByIndex(index, title);
            updateBalance();
            System.out.println("Movie rented successfully.");
        } else {
            System.out.println("Invalid index. Choose between 0-4.");
        }
    }

    //- updateBalance(): void
    // Must be called at the end of both rentMovie and dropMovieByID
    private void updateBalance() {
        int rentedCount = 0;
        for (int id : movieIDs) {
            if (id != 9999) {
                rentedCount++;
            }
        }
        balanceOwed = rentedCount * 3.99; // number of rented movies × 3.99
    }

    //    Display all ra information.
    public void displayAccountInfo() {
        System.out.println("-------------------displayAccountInfo() START-------------------");
        System.out.println("Store: " + storeName);
        System.out.println("Account ID: " + accountID);
        System.out.println("Name: " + getFullName());
        System.out.println("Balance Owed: $" + balanceOwed);
        System.out.println("-------------------displayAccountInfo() END-------------------");
    }

    public static void main(String[] args) {
        RentalAccount ra = new RentalAccount();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your information below.");
        System.out.print("Please enter your ID: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input");
            sc.next();
        }
        int id = sc.nextInt();
        ra.setAccountID(id);
        sc.nextLine(); // fix \n
        System.out.print("Please enter your first name: ");
        String fn = sc.nextLine();
        ra.setFirstName(fn);
        System.out.print("Please enter your last name: ");
        String ln = sc.nextLine();
        ra.setLastName(ln);

        // Allow editing of basic account information: setter
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Movie Rental Menu ---");
            System.out.println("1. Display account info");
            System.out.println("2. Edit account info");
            System.out.println("3. Display rented movies");
            System.out.println("4. Rent a movie");
            System.out.println("5. Drop a movie");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // display the account information
                    ra.displayAccountInfo();
                    break;
                case 2:
                    System.out.print("Enter new account ID: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input");
                        sc.next();
                    }
                    ra.setAccountID(sc.nextInt());
                    sc.nextLine();
                    System.out.print("Enter new first name: ");
                    ra.setFirstName(sc.nextLine());
                    System.out.print("Enter new last name: ");
                    ra.setLastName(sc.nextLine());
                    System.out.println("Account info updated.");
                    break;
                case 3:
                    ra.printRentedMovies();
                    break;
                case 4:
                    System.out.print("Enter index to rent (0-4): ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input");
                        sc.next();
                    }


                    int index = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter movie ID: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input");
                        sc.next();
                    }
                    int movieID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter movie title: ");
                    String title = sc.nextLine();
                    ra.rentMovie(index, movieID, title);
                    break;
                case 5:
                    System.out.print("Enter movie ID to drop: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input");
                        sc.next();
                    }
                    int idToDrop = sc.nextInt();
                    sc.nextLine();
                    ra.dropMovieByID(idToDrop);
                    break;
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

}
