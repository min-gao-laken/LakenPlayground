package lab2.array.arrayminibook;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-23
 * @description ----------------------------------------
 * 1. Create two methods sum(int[]) and sum(double[]).
 * 2. Test both with different arrays.
 * 3. Print the total for each version.
 * ----------------------------------------
 */
public class Chapter12_OverloadingArrays {
    public static void main(String[] args) {
        Random r = new Random();
//        double value = Math.round(r.nextDouble() * 10000.0) / 100.0;

        int[] intArray = new int[3];
        double[] doubleArray = new double[3];

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = r.nextInt(101);
            System.out.println(intArray[i]);
        }
        System.out.println("----------------------------------------");
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = Math.round(r.nextDouble() * 10000.0) / 100.0;
            System.out.println(doubleArray[i]);
        }

        //sum(int[]) and sum(double[]).

        int sum1 = sum(intArray);
        double sum2 = sum(doubleArray);
        System.out.println("The results are as follows: " + sum1 + " and " + sum2 + ".");
    }

    public static int sum(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum = i + sum;
        }
        return sum;
    }

    public static double sum(double[] arr) {
        double sum = 0;
        for (double i : arr) {
            sum = i + sum;
        }
        return sum;
    }
}
