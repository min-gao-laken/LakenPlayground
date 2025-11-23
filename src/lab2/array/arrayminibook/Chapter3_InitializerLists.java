package lab2.array.arrayminibook;

/**
 * @author Laken
 * @date 2025-11-19
 * @description ----------------------------------------
 * 1. Declare a double array named prices with these values:
 * 2.5, 3.9, 4.1, 5.75
 * 2. Print all values and then compute their total.
 * 3. Predict the expected output before running your program.
 * ----------------------------------------
 */
public class Chapter3_InitializerLists {
    public static void main(String[] args) {
//        SYNTAX:
//        dataType[] arrayName = { value1, value2, value3 };
        double[] prices = {2.5, 3.9, 4.1, 5.75};
        double sum = 0;
        for (double i : prices) {
            System.out.println(i);
            sum = sum + i;
        }

        System.out.printf("The total is %.2f", sum);

    }
}

//----------------------------------------
//The two statements below create identical arrays:
//
//int[] a = {3, 5, 6};
//int[] b = new int[3];
//b[0] = 3; b[1] = 5; b[2] = 6;
//
//Both arrays have the same content.
//The first approach (initializer list) is shorter and clearer when values are known ahead of time.
//        ----------------------------------------