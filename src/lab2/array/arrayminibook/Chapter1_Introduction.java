package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-19
 * @description Create a new program that:
 * 1. Declares an array of 3 temperatures (in Celsius)
 * 2. Assigns values manually
 * 3. Prints them all with their average temperature
 */
public class Chapter1_Introduction {
    public static void main(String[] args) {
        double[] arrayTemperatures = new double[3];
        arrayTemperatures[0] = 23.1;
        arrayTemperatures[1] = -10;
        arrayTemperatures[2] = 27.9;
        double sum = 0;
        for (double i : arrayTemperatures) {
            sum = sum + i;
        }
        System.out.printf("The sum is %.2f.", sum);
        System.out.printf("%n");
        double average = sum / arrayTemperatures.length;
        System.out.printf("The average temperature is %.2f.", average);
    }
}
