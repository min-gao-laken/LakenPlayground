package lab2.array;

/**
 * @author Laken
 * @date 2025-11-16
 * @description
 */
public class ArrayExample2 {
    public static void main(String[] args) {
// Create a program that keeps track of the highest and lowest values in an array:
        int[] numbers = {45, 12, 98, 33, 27};
        int max = numbers[0];
        int min = numbers[0];
        for (int n : numbers) {
            if (n > max) {
                max = n;
            }
            if (min > n) {
                min = n;
            }
        }
        System.out.println("max: " + max);
        System.out.println("min: " + min);
    }
}

