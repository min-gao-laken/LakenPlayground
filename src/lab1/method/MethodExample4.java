package lab1.method;

/**
 * @author Laken
 * @date 2025-11-12
 * @description recursion
 */
public class MethodExample4 {
    static void countdown(int n) {
        if (n > 0) {
            System.out.print(n + " "); // 5 4 3 2 1
            countdown(n - 1);
            System.out.print(n + " "); // 1 2 3 4 5
        }
    }

    public static void main(String[] args) {
        countdown(5);

    }
}
