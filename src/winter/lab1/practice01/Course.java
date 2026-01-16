package winter.lab1.practice01;

/**
 * @author Laken
 * @date 2026-01-13
 * @description
 */
public class Course {
    private int courseCode;
    private String courseName;

    // blank constructor
    public Course() {
        courseCode = 999;
    }

    // parameterized constructor
    public Course(int courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    // getters and setters
    public int getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String toString() {
//        return "hello world!";
        return courseCode + ": " + courseName;
    }

    public static void main(String[] args) {

    }
}
