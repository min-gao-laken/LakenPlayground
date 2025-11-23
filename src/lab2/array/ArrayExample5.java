package lab2.array;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-17
 * @description
 */
public class ArrayExample5 {
    public static void main(String[] args) {
//        1. Generate an array of 20 random integers (0–100).
//        2. Compute and print how many numbers are ABOVE the average.
//        3. Hint: use a second loop and an if condition.
        Random rand = new Random();
        int[] array = new int[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(101);
            System.out.print(array[i] + " ");
        }

        int sum = 0;
        for (int i : array) {
            sum += i;
        }

        int average = 0;
        average = sum / array.length;

        int counter = 0;
        for (int i : array) {
            if (i > average) {
                counter++;
            }
        }

        System.out.println(average);
        System.out.println(counter);
    }
}
