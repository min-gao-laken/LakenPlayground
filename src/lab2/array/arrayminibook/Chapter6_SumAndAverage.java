package lab2.array.arrayminibook;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-19
 * @description ----------------------------------------
 * 1. Generate an array of 20 random integers (0–100).
 * 2. Compute and print how many numbers are ABOVE the average.
 * 3. Hint: use a second loop and an if condition.
 * ----------------------------------------
 */
public class Chapter6_SumAndAverage {
    public static void main(String[] args) {
        int[] randomArray = new int[20];
        double average = 0;
        int sum = 0;
        int counter = 0;
        Random rand = new Random();
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = rand.nextInt(101);
        }
        for (int i = 0; i < randomArray.length; i++) {
            System.out.printf("The 20 random integers is %d -> %d.%n", i, randomArray[i]);
            sum = sum + randomArray[i];
            average = (double) sum / randomArray.length;

            if (randomArray[i] > average) {
                counter++;
            }
        }

        System.out.println("How many numbers are ABOVE the average: " + counter);


    }
}
