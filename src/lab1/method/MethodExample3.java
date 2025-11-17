package lab1.method;

/**
 * @author Laken
 * @date 2025-11-12
 * @description recursion
 */
public class MethodExample3 {
    public static int sum(int k) {
        if (k > 0) {
            return k + sum(k - 1);
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int result = sum(10);
        System.out.println(result);
    }
}
