package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-02
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class called Student with private fields:
 * - name
 * - gpa
 * 2. Write setGpa(double value) so it accepts only values
 * between 0.0 and 4.0, ignoring anything outside the range.
 * 3. Write getGpa() and a display method to show name and GPA.
 * 4. Create a Student object and test all methods.
 * ------------------------------------------------------------
 */
public class Chapter7_GettersAndSetters {
    static class Student {
        private String name;
        private double gpa;

        public void setGpa(double g) {
            if (g >= 0 && g <= 4) {
                this.gpa = g;
            }
        }

        public void setName(String n) {
            this.name = n;
        }

        public double getGpa() {
            return this.gpa;
        }

        public String getName() {
            return this.name;
        }


    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setGpa(3.8);
        System.out.println(student.getGpa());
        System.out.println(student.getName());
        student.setName("Laken");
        System.out.println(student.getName());
    }
}
