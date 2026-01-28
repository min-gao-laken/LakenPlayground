package winter.lab1.derivation;

import kotlin.text.UStringsKt;

/**
 * @author Laken
 * @date 2026-01-27
 * @description
 */
public class Person {
    private String firstName;
    private String lastName;
    private char middleInit;

    // 别漏掉了
    public Person() {
    }

    public Person(String firstName, String lastName, char middleInit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInit = middleInit;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getMiddleInit() {
        return middleInit;
    }

    public void setMiddleInit(char middleInit) {
        this.middleInit = middleInit;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + ", " + middleInit + ".";
    }

//    public static void main(String[] args) {
//        Person p1 = new Person("Mike", "Va", 'J');
//        System.out.println(p1);
//
//    }
}
