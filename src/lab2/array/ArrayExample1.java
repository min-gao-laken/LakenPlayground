package lab2.array;

/**
 * @author Laken
 * @date 2025-11-16
 * @description
 */
public class ArrayExample1 {
    public static void main(String[] args) {
        int[] ages = {20, 22, 18, 35, 48, 26, 87, 70};
        //  Print the average
        double avg, sum = 0;
        for (int i : ages) {
            sum += i;
        }
        avg = sum / ages.length;
        System.out.println("Average is " + avg);

//      And in this example, we create a program that finds the lowest age among different ages:
        int lowestAge = ages[0];
        for (int i : ages) {
            if (lowestAge > i) {
                lowestAge = i;
            }
        }
        System.out.println("Lowest age is " + lowestAge);

        // Create a program with a list of numbers where you want to skip negative values,
        // but stop completely if you find a zero:
        int[] numbers = {3, -1, 7, 0, 9};
        for (int number : numbers) {
            if (number < 0) {
                // 如果 number 小于 0，执行 continue，意思是 跳过本次循环的剩余部分，直接进入下一次循环。
                // 在这个数组里，-1 会触发 continue，因此不会打印 -1。
                continue;
            }
            if (number == 0) {
                // 如果 number 等于 0，执行 break，意思是 直接退出整个循环。
                //在这个数组里，当 number 是 0 时，循环会停止，后面的 9 就不会被处理。
                break;
            }
            System.out.println(number);
        }
    }
}
