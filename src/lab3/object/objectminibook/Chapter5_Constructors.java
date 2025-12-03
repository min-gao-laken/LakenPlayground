package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a Rectangle class with private width and height.
 * 2. Add two constructors:
 * - A default constructor that sets both to 1.0
 * - A parameterized constructor that accepts width and height
 * 3. Create objects using both constructors and print their
 * areas to confirm the initialization works correctly.
 * ------------------------------------------------------------
 */
public class Chapter5_Constructors {
    static class Rectangle {
        private double width;
        private double height;

        public Rectangle() {
            width = 1.0;
            height = 1.0;
        }

        public void getArea() {
            System.out.println(width * height);
        }

        public Rectangle(double width, double h) {
            if (width > 0) { // try 'this.width = width' vs 'width = w'
                this.width = width;
            } else {
                this.width = 1.0;
            }
            if (h > 0) {
                height = h;
            } else {
                height = 1.0;
            }
        }
    }

    public static void main(String[] args) {
        Rectangle r1 = new Rectangle();
        r1.getArea();

        Rectangle r2 = new Rectangle(2, 10);
        r2.getArea();
    }
}
