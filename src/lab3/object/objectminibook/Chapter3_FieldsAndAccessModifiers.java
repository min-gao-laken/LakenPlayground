package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class called Student with private fields:
 * - name
 * - grade
 * 2. Add a method setGrade(int g) that only accepts values
 * between 0 and 100.
 * 3. Create a Student object, set its grade, then print the
 * stored value using a getGrade() method.
 * ------------------------------------------------------------
 */
public class Chapter3_FieldsAndAccessModifiers {
    static class Student {
        private String name;
        private int grade;

        public void setGrade(int g) {
            if (g >= 0 && g <= 100) {
                grade = g;
            }
        }

        public void getGrade() {
            System.out.println("name is: " + name + ", grade is: " + grade + ".");
        }
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "Laken";
        s1.grade = 1;
        s1.setGrade(10);
        s1.setGrade(101);
        s1.getGrade();

        Student s2 = new Student();
        s2.name = "Jen";
        s2.grade = 1;
        s2.getGrade();


    }
}
