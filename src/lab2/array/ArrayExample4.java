package lab2.array;

/**
 * @author Laken
 * @date 2025-11-17
 * @description
 */
public class ArrayExample4 {
    public static void main(String[] args) {
//        1. Modify the program to display BOTH the largest and smallest values.
//        2. Then display the difference between them.
//        3. Challenge: count how many elements are equal to the smallest value.

        int[] numbers = {45, 67, 23, 12, 89, 12, 99, 54, 12, 12};
        int max = numbers[0];
        int min = numbers[0];
        int counter = 0;

        for (int i : numbers) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }

        }

        for (int i : numbers) {
            if (i == min) {
                counter++;
            }
        }

        System.out.println(max);
        System.out.println(min);
        System.out.println(max - min);
        System.out.println(counter);
    }
}
