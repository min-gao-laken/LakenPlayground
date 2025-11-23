package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-19
 * @description ----------------------------------------
 * 1. Create a String array named fruits with size 3.
 * 2. Assign three fruit names.
 * 3. Print each with its index number.
 * ----------------------------------------
 */
public class Chapter2_Declaring {
    public static void main(String[] args) {
        String[] fruits = new String[3];
        fruits[0] = "Apple";
        fruits[1] = "Orange";
        fruits[2] = "Pear";

        for (int i = 0; i < fruits.length; i++) {
            System.out.printf("%s is at index %d. %n", fruits[i], i);
        }
    }
}
