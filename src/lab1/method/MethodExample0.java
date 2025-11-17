package lab1.method;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-12
 * @description Random number generation
 */
public class MethodExample0 {
    public static void main(String[] args) {

        // Approach 1: Math.random(), [1, 100]
        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("Random number (1–100 inclusive): " + randomNumber);

        // Approach 2
        Random rand = new Random();
        int randomNumber2 = rand.nextInt(100) + 1;
        // nextInt(100) → [0, 99]
        System.out.println("Random number (1–100 inclusive): " + randomNumber2);
    }
}
