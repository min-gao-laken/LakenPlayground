package winter.lab1;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2026-01-10
 * @description
 */
public class Practice_01 {
    public static void wrongWay() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter your age: ");
        int age = input.nextInt(); // 只读数字，不读回车

        System.out.print("Enter your name: ");
        String name = input.nextLine(); // 会直接读到残留回车

        System.out.println("Age: " + age);
        System.out.println("Name: '" + name + "'");

        input.close();
//        output:
//        Enter your age: 12
//        Enter your name: Age: 12
//        Name: '
    }

    public static void correctWay() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = input.nextInt();
        input.nextLine(); // ⚠️ 清空残留回车

        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.println("Age: " + age);
        System.out.println("Name: '" + name + "'");

//        output:
//        Enter your age: 12
//        Enter your name: d
//        Age: 12
//        Name: 'd'
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word = input.next();
        System.out.println(word);

        Scanner input2 = new Scanner(System.in);
        System.out.print("Enter a line: ");
        String line = input2.nextLine();
        System.out.println(line);

        Scanner input3 = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = input3.nextInt();
        System.out.println(age);

        correctWay();
        wrongWay();

    }
}
