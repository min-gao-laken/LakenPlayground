package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-23
 * @description ----------------------------------------
 * 1. Create a 2x3 int matrix of your own data.
 * 2. Print it using nested loops.
 * 3. Then compute the sum of all numbers in the matrix.
 * ----------------------------------------
 */
public class Chapter14_EnhancedForAnd2D {
    public static void main(String[] args) {
//        int[][] array = new int[2][3];
        int[][] array = {{1, 2, 3}, {100, 200, 300}};
        int sum = 0;
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                System.out.print(array[row][col] + " ");
                sum = array[row][col] + sum;
            }
        }
        System.out.println("\nThe sum is: " + sum);

    }
}
