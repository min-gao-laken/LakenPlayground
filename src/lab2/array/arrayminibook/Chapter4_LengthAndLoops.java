package lab2.array.arrayminibook;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-19
 * @description ----------------------------------------
 * 1. Create an array of 8 random integers between 1–50.
 * 2. Use a for loop with .length to print all elements.
 * 3. Then, print only the elements that are divisible by 5.
 * ----------------------------------------
 */
public class Chapter4_LengthAndLoops {
    public static void main(String[] args) {
        Random rand = new Random();

        int[] randomArray = new int[8];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = rand.nextInt(50) + 1;  // [1,50]
            System.out.printf("Random array element is %d.%n", randomArray[i]);

            if (randomArray[i] % 5 == 0) {
                System.out.printf("__________________Found divisible by 5: %d%n", randomArray[i]);
            }
        }
    }
}
