package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Add an updateAge(int newAge) method to the Person class.
 * 2. Create two Person objects with different ages.
 * 3. Update the age of only one object, then print both objects
 * to verify that each instance maintains its own data.
 * ------------------------------------------------------------
 */
public class Chapter2_ClassesAndInstances {
    static class Person {
        String name;
        int age;

        public void introduce() {
            System.out.println(name + " is " + age + " years old.");
        }

        public void updateAge(int newAge) {
            age = newAge;
        }
    }

    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "Laken";
        p1.age = 20;

        Person p2 = new Person();
        p2.name = "Jen";
        p2.age = 20;
        p1.introduce();
        p2.introduce();
        p1.updateAge(22);
        System.out.println("After updateAge:");
        p1.introduce();
        p2.introduce();
    }
}
