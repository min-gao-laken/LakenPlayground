package lab2.array.arrayminibook;

import java.util.Arrays;

/**
 * @author Laken
 * @date 2025-11-23
 * @description ----------------------------------------
 * 1. Extend the Stock Price Analyzer to calculate:
 * – number of days below average
 * – total cost of buying one share daily
 * 2. Extend the Student Grades Calculator to:
 * – add two new students
 * – display highest and lowest final grades
 * – count how many passed (grade ≥ 60)
 * ----------------------------------------
 */
public class Chapter16_ProjectChallenge {
    public static void main(String[] args) {
//        liveDemo24();
        liveDemo25();
    }

    public static void liveDemo24() {

        System.out.println("LIVE DEMO 24: Stock Price Analyzer (Guided Project)");
        System.out.println("---------------------------------------------------");

        double[] closingPrice = {25.0, 38.25, 39.50, 38.75, 37.33, 37.22, 29.56, 31.05, 30.77, 38.25};

        // Step 1 – Display all data
        System.out.println("Stock closing prices: " + Arrays.toString(closingPrice));

        // Step 2 – Find highest, lowest, and average
        double max = closingPrice[0];
        double min = closingPrice[0];
        double sum = 0;

        for (double price : closingPrice) {
            if (price > max) max = price;
            if (price < min) min = price;
            sum += price;
        }

        double avg = sum / closingPrice.length;

        // Step 3 – Display summary
        System.out.printf("Highest Price: %.2f%n", max);
        System.out.printf("Lowest Price:  %.2f%n", min);
        System.out.printf("Average Price: %.2f%n", avg);

        int counter = 0;
        for (double price : closingPrice) {
            if (price < avg) {
                counter++;
            }
        }
        System.out.printf("Number of days below average: %d%n", counter);
        System.out.printf("Total cost of buying one share daily: %.2f%n", sum);

    }

    public static void liveDemo25() {

        System.out.println("LIVE DEMO 25: Student Grades Calculator (Guided Project)");
        System.out.println("--------------------------------------------------------");

        String[] name = {"Robin", "Jo", "Kelly", "Jaimie", "Min", "Laken"};
        int[] midtermScore = {28, 78, 92, 83, 99, 98};
        int[] finalScore = {58, 75, 96, 79, 88, 87};
        int[] assignmentGrade = {33, 80, 90, 83, 90, 98};

        // Step 1 – Display arrays
        System.out.println("Names: " + Arrays.toString(name));
        System.out.println("Midterm: " + Arrays.toString(midtermScore));
        System.out.println("Final: " + Arrays.toString(finalScore));
        System.out.println("Assignment: " + Arrays.toString(assignmentGrade));

        // Step 2 – Compute final grades
        int[] finalGrade = new int[name.length];
        for (int i = 0; i < name.length; i++) {
            finalGrade[i] = (int) (assignmentGrade[i] * 0.15 +
                    midtermScore[i] * 0.40 +
                    finalScore[i] * 0.45);
        }

        // Step 3 – Display each student’s final grade
        double max = finalGrade[0];
        double min = finalGrade[0];
        int counter = 0;
        System.out.println("\nFinal Grades:");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i] + " → " + finalGrade[i]);
            if (finalGrade[i] > max) {
                max = finalGrade[i];
            }
            if (finalGrade[i] < min) {
                min = finalGrade[i];
            }
            if (finalGrade[i] >= 60) {
                counter++;
            }
        }

        // Step 4 – Find class average
        double sum = 0;
        for (int grade : finalGrade)
            sum += grade;

        System.out.printf("%nClass Average: %.2f%n", sum / finalGrade.length);

        System.out.printf("%nDisplay highest and lowest final grades as follows: %.2f, %.2f%n", max, min);
        System.out.printf("%nCount how many passed (grade ≥ 60): %d%n", counter);

    }

}
