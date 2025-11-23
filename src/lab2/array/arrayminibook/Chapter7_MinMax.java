package lab2.array.arrayminibook;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-22
 * @description ----------------------------------------
 * 1. Modify the program to display BOTH the largest and smallest values.
 * 2. Then display the difference between them.
 * 3. Challenge: count how many elements are equal to the smallest value.
 * ----------------------------------------
 */
public class Chapter7_MinMax {
    public static void main(String[] args) {
        int[] array = {45, 67, 23, 89, 12, 99, 54};

        int largestValue = array[0];
        int smallestValue = array[0];
        int counter = 0;

        for (int i : array) {
            if (i > largestValue) {
                largestValue = i;
            }
            if (i < smallestValue) {
                smallestValue = i;
            }
        }

        for (int i : array) {
            if (i == smallestValue) {
                counter++;
            }
        }

        System.out.println("largestValue: " + largestValue);
        System.out.println("smallestValue: " + smallestValue);
        System.out.println("difference: " + (largestValue - smallestValue));
        System.out.printf("Counter: %d\n", counter);
    }
}
