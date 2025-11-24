package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-23
 * @description ----------------------------------------
 * 1. Write a program that intentionally causes ArrayIndexOutOfBounds.
 * 2. Then fix it by adjusting your loop condition.
 * 3. Add a print statement inside the catch block to confirm recovery.
 * ----------------------------------------
 */
public class Chapter15_DebuggingArrays {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};

//        System.out.println("numbers[5] = " + numbers[5]);

//        Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 5
//        at lab2.array.arrayminibook.Chapter15_DebuggingArrays.main(Chapter15_DebuggingArrays.java:16)


        try {
            System.out.println("numbers[5] = " + numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR: Tried to access invalid index!");
        }

    }
}
