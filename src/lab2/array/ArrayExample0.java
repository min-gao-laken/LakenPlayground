package lab2.array;

/**
 * @author Laken
 * @date 2025-11-16
 * @description
 */
public class ArrayExample0 {
    public static void main(String[] args) {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        System.out.println(cars[0]);
        System.out.println(cars.length);

        String[] cars2 = new String[4];
        cars2[0] = "Volvo";
        cars2[1] = "BMW";
        cars2[2] = "Mazda";
        cars2[3] = "Ford";
        System.out.println(cars2[0]);

        String[] cars3 = new String[]{"Volvo", "BMW", "Ford", "Mazda"};
        System.out.println(cars3[0]);

        for (int i = 0; i < cars3.length; i++) {
            System.out.println(cars3[i]);
        }


        for (String s : cars3) {
            System.out.println(s);
        }

        int[] numbers = {1, 2, 3};
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum = sum + numbers[i];
        }
        for (int i: numbers) {
            System.out.println(i);
        }
        System.out.println(sum);

    }
}
