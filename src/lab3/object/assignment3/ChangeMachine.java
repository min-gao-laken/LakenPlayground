package lab3.object.assignment3;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2025-12-04
 * @description
 */
public class ChangeMachine {
    // Attributes
    private int loonies;
    private int toonies;
    private boolean status;

    // Constructor
    public ChangeMachine(int initialLoonies, int initialToonies) {
        setLoonies(initialLoonies);
        setToonies(initialToonies);
        checkStatus();
    }

    public int getLoonies() {
        return loonies;
    }

    public void setLoonies(int num) {
        if (num < 0) {
            System.out.println("You cannot have a negative number of coins!");
        } else {
            this.loonies += num; // Add coins to existing supply
        }
        checkStatus();
    }

    public int getToonies() {
        return toonies;
    }

    public void setToonies(int num) {
        if (num < 0) {
            System.out.println("You cannot have a negative number of coins!");
        } else {
            this.toonies += num; // Add coins to existing supply
        }
        checkStatus();
    }

    public boolean getStatus() {
        return status;
    }

    public void checkStatus() {
        if (loonies >= 1 && toonies >= 2) {
            status = true;
        } else {
            status = false;
        }
    }

    public void makeChange(int bill) {
        int changeLoonies = 0;
        int changeToonies = 0;

        switch (bill) {
            case 5:
                changeLoonies = 1;
                changeToonies = 2;
                break;
            case 10:
                changeLoonies = 0;
                changeToonies = 5;
                break;
            case 20:
                changeLoonies = 0;
                changeToonies = 10;
                break;
        }

        if (loonies < changeLoonies || toonies < changeToonies) {
            System.out.println("Out of coins to give change!");
            status = false;
            return;
        }

        loonies -= changeLoonies;
        toonies -= changeToonies;

        System.out.println("Change given: " + changeLoonies + " loonies, " + changeToonies + " toonies");
        checkStatus();
    }

    public void acceptMoney(int bill) {
        if (!status) {
            System.out.println("Out of Order! Here is your bill back.");
            return;
        }

        if (bill != 5 && bill != 10 && bill != 20) {
            System.out.println("You must insert a $5 or $10 or $20 bill. Try again.");
            return;
        }

        makeChange(bill);
    }

    public void refillMachine(int pin, int newLoonies, int newToonies) {
        if (pin != 333) {
            System.out.println("Invalid PIN!");
            return;
        }
        setLoonies(newLoonies);
        setToonies(newToonies);
        System.out.println("Machine is ready.");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ChangeMachine machine = new ChangeMachine(5, 10);

        if (!machine.getStatus()) {
            System.out.println("Out of order! Cannot accept ANY bills. Please call technician to repair the machine.");
            return;
        }

        while (true) {
            System.out.println("\nInsert a bill ($5, $10, $20) or enter 0 for technician:");
            int bill = scanner.nextInt();

            if (bill == 0) {
                System.out.println("Enter technician PIN:");
                int pin = scanner.nextInt();

                System.out.println("Enter number of loonies to add:");
                int newLoonies = scanner.nextInt();

                System.out.println("Enter number of toonies to add:");
                int newToonies = scanner.nextInt();

                machine.refillMachine(pin, newLoonies, newToonies);

                if (!machine.getStatus()) {
                    System.out.println("Machine still out of order!");
                }
            } else {
                machine.acceptMoney(bill);

                if (!machine.getStatus()) {
                    System.out.println("Out of order! Cannot accept any more bills. Please call the technician to refill the machine.");
                    break;
                }
            }
        }

        scanner.close();
    }
}
