package lab1.method;

import java.util.Random;

/**
 * @author Laken
 * @date 2025-11-12
 * @description
 */
public class MethodExample5 {
    public static void main(String[] args) {
        Random rd = new Random();
        int n = rd.nextInt(100) + 1; // [1,100]
        System.out.println(n);

        // print [6,10]
        int k = rd.nextInt(5) + 6; //[0,4) -> [6,10]
        System.out.println(k);

        // print [20,100]
        int j = rd.nextInt(81)+20; // [0,80) -> [20,100]
    }
}
