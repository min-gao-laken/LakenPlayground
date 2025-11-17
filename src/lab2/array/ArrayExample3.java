package lab2.array;

/**
 * @author Laken
 * @date 2025-11-16
 * @description
 */
public class ArrayExample3 {
    public static void main(String[] args) {
        int[][] myNumbers = {{1, 2, 3}, {4, 5, 6, 99}};
        System.out.println(myNumbers.length);
        System.out.println(myNumbers[0].length);
        System.out.println(myNumbers[1].length);
        System.out.println(myNumbers[1][3]);
        // Use a for loop inside another for loop to visit every element (row by row):

        for (int row = 0; row < myNumbers.length; row++) {
            for (int col = 0; col < myNumbers[row].length; col++) {
                System.out.println("myNumbers[" + row + "][" + col + "] = " + myNumbers[row][col]);
            }
        }

        for (int[] row : myNumbers) {
            for (int num : row) {
                System.out.println(num);
            }
        }

    }
}
