package winter.lab1.practice01;

/**
 * @author Laken
 * @date 2026-01-13
 * @description
 */
public class CompositionInClassB {
    public static void main(String[] args) {
/**
 *
 */

        Student s1 = new Student(1001, "Mike");
        Course c1 = new Course(233, "OOP with Java");

        // add course c1 to the students courses array at position 0
        s1.enroll(0, c1);

        // short cut: sout
        System.out.println("The student has been enrolled in: " + c1);

        System.out.println("The student has been enrolled in: " + s1.getCourseByIndex(0));
        System.out.println("The course name is: " + s1.getCourseByIndex(0).getCourseName());

        s1.getCourseByIndex(0).setCourseName("Advanced OOP with Java");
        System.out.println("The course name has been updated: " + s1.getCourseByIndex(0).getCourseName());
    }
}
