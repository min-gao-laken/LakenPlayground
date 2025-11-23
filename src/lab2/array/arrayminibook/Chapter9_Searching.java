package lab2.array.arrayminibook;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2025-11-22
 * @description ----------------------------------------
 * 1. Write a program that asks the user for a number and reports whether it exists in an array.
 * 2. Then count how many times it appears.
 * 3. Challenge: extend this idea to count how many scores are above 90.
 * ----------------------------------------
 */
public class Chapter9_Searching {
    public static void main(String[] args) {
        int[] array = {1, 2, 2, 1, 3, 2, 3, 1, 2, 1};
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a number you like.");
        int inputValue = sc.nextInt();

        double[] scores = {92.5, 88.0, 76.3, 92.5, 100, 67.8, 88.8, 73.4, 92.5, 92.5};

        search(array, inputValue);
        int scoreCounter = aboveScoreCounter(scores);
        System.out.println("how many scores are above 90: " + scoreCounter);
    }

    public static void search(int[] array, int value) {
        boolean isExist = false;
        int counter = 0;
        for (int i : array) {
            if (value == i) {
                isExist = true;
                counter++;
            }
        }
        System.out.println("isExist: " + isExist);
        System.out.println("counter: " + counter);
    }

    public static int aboveScoreCounter(double[] array) {
        int counter = 0;
        for (double i : array) {
            if (i > 90) {
                counter++;
            }
        }
        return counter;
    }
}
