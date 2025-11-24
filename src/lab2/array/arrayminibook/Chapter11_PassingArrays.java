package lab2.array.arrayminibook;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-23
 * @description Arrays are passed by reference
 * ----------------------------------------
 * 1. Write a method getMax(int[] arr) that returns the largest value.
 * 2. In main(), create an array of random numbers and call getMax().
 * 3. Then write a second method getMin(int[] arr) and test it.
 * ----------------------------------------
 */
public class Chapter11_PassingArrays {
    public static void main(String[] args) {
        Random random = new Random();
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(101); // [0,100]
        }
        int maxValue = getMax(array);
        System.out.println("The largest value is: " + maxValue);

        int minValue = getMin(array);
        System.out.println("The smallest value is: " + minValue);

    }

    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int n : arr) {
            System.out.println(n);
            if (n > max) {
                max = n;
            }
        }
        return max;
    }

    public static int getMin(int[] arr) {
        int min = arr[0];
        for (int n : arr) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }
}
