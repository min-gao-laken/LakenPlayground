package lab2.array.arrayminibook;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-23
 * @description ----------------------------------------
 * 1. Create an array of 10 random integers.
 * 2. Sort it using Arrays.sort().
 * 3. Then reverse it manually to show descending order.
 * ----------------------------------------
 */
public class Chapter13_SortingAndReversing {
    public static void main(String[] args) {
        Random random = new Random();
        int[] array = new int[5];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(101); // [0,100]
        }

        System.out.println("Original array: " + Arrays.toString(array));

        Arrays.sort(array);
        System.out.println("Sorted array: " + Arrays.toString(array)); // ascending

        int[] descending = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            descending[i] = array[array.length - 1 - i];
        }
        System.out.println("Sorted array: " + Arrays.toString(descending)); // descending
    }
}
