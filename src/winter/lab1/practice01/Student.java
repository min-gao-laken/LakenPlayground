package winter.lab1.practice01;

/**
 * @author Laken
 * @date 2026-01-13
 * @description
 */
public class Student {
    private int id;
    private String name;
    private Course[] courses;

    public Student() {

    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new Course[10];
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Allows course to be added to students courses array
    public void enroll(int index, Course course) {
        courses[index] = course;
    }

    /**
     * This function returns a course from the students course array at a given position
     *
     * @param index
     * @return
     */
    public Course getCourseByIndex(int index) {
        Course result = new Course();
        result = courses[index];
        return result;
    }

    public static void main(String[] args) {

    }
}
