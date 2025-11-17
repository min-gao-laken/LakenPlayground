package lab1.method;

/**
 * @author Laken
 * @date 2025-11-12
 * @description TODO: MethodExample1
 */
public class MethodExample1 {
    static void myMethod() {
        System.out.println("Method Example 1");
    }

    static int add5(int a) {
        return a + 5;
    }

    //    overloading
    static int plusMethod(int a, int b) {
        System.out.println("Try overloading, int type");
        return a + b;
    }

    static double plusMethod(double a, double b) {
        System.out.println("Try overloading, doubles type");
        return a + b;
    }

    int x = 5;
    static int y = 100;

    public static void main(String[] args) {
        myMethod();
        System.out.println(add5(12));

        System.out.println(plusMethod(1, 2));
        System.out.println(plusMethod(1.5, 2.2));

//        System.out.println(x); It is wrong!
        System.out.println(y); // It is correct!
    }
}


