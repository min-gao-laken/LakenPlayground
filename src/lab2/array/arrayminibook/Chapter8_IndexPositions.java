package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-22
 * @description ----------------------------------------
 * 1. Write a method getMaxIndex(int[] arr) that returns the index of the largest element.
 * 2. Test it using the array {10, 4, 21, 8, 19}.
 * 3. Then print both value and index.
 * ----------------------------------------
 */
public class Chapter8_IndexPositions {
    public static void main(String[] args) {
        int[] array = {10, 4, 21, 8, 19};
        getMaxIndex(array);
    }

    public static void getMaxIndex(int[] array) {
        int maxIndex = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }

        System.out.println("maxIndex: " + maxIndex);
        System.out.println("maxValue: " + array[maxIndex]);

    }
}

