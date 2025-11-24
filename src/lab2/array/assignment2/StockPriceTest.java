package lab2.array.assignment2;

import java.util.Arrays;

/**
 * @author Laken
 * @date 2025-11-23
 * @description
 */
public class StockPriceTest {
    public static void main(String[] args) {
        double[] closingPrice = {25.0, 38.25, 39.50, 38.75, 37.33, 37.22, 29.56, 31.05, 30.77, 38.25};

        double max = closingPrice[0];
        double min = closingPrice[0];
        double sum = 0;

        for (double price : closingPrice) {
            if (price > max) {
                max = price;
            }
            if (price < min) {
                min = price;
            }
            sum += price;
        }

        double avg = sum / closingPrice.length;

        System.out.printf("Highest closing price: %.2f%n", max);
        System.out.printf("Lowest closing price:  %.2f%n", min);
        System.out.printf("Average closing price: %.2f%n", avg);

        int days = countBelowAverage(closingPrice, avg);
        System.out.printf("Number of days below average: %d%n", days);

        System.out.printf("Total cost of buying one share daily: %.2f%n", sum);

        double[] priceSummary = new double[3];

        priceSummary[0] = max;
        priceSummary[1] = min;
        priceSummary[2] = avg;

        System.out.println("Price summary: " + Arrays.toString(priceSummary));
        closingPrice = reverseSort(closingPrice);
        System.out.println("Closing prices in descending order: " + Arrays.toString(closingPrice));
    }

    public static int countBelowAverage(double[] arr, double avg) {
        int count = 0;
        for (double price : arr) {
            if (price < avg) count++;
        }
        return count;
    }

    public static double[] reverseSort(double[] arr) {
        double[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr);

        for (int i = 0; i < sorted.length / 2; i++) {
            double temp = sorted[i];
            sorted[i] = sorted[sorted.length - 1 - i];
            sorted[sorted.length - 1 - i] = temp;
        }

        return sorted;
    }
}
