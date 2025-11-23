package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-22
 * @description ----------------------------------------
 * 1. Create two arrays: product[] and price[].
 * 2. Display each product name with its price.
 * 3. Then find the product with the highest price.
 * ----------------------------------------
 */
public class Chapter10_ParallelArrays {
    public static void main(String[] args) {
        String[] product = {"MacBook", "iPhone", "Apple Watch", "Coffee Maker"};
        double[] price = {11, 12.11, 13.2, 99.12};

        int targetIndex = highestPrice(price);

        System.out.printf("product with the highest price is %s. %n", product[targetIndex]);

        displayArray(product, price);
    }

    public static int highestPrice(double[] price) {
        int highestPriceIndex = 0;
        for (int i = 0; i < price.length; i++) {
            if (price[highestPriceIndex] < price[i]) {
                highestPriceIndex = i;
            }
        }
        return highestPriceIndex;
    }

    public static void displayArray(String[] product, double[] price) {
        for (int i = 0; i < price.length; i++) {
            System.out.println(product[i] + "'s price is: " + price[i] + ". And index is: " + i);
        }
    }
}
