package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Draw a UML diagram for a Rectangle class with fields:
 * - width (private)
 * - height (private)
 * 2. Add methods:
 * - setWidth(w)
 * - setHeight(h)
 * - getArea()
 * 3. Convert your UML into a Java class and create one object
 * to test all methods.
 * ------------------------------------------------------------
 */
public class Chapter4_UMLClassStructure {
    /*
      UML for Rectangle:

      +-------------------------+
      |        Rectangle        |
      +-------------------------+
      | - width : double        |
      | - height : double       |
      +-------------------------+
      | + setWidth(w: double)   |
      | + setHeight(h: double)  |
      | + getArea()             |
      +-------------------------+
  */

    static class Rectangle {
        private double width;
        private double height;

        public void setWidth(double w) {
            width = w;
        }

        public void setHeight(double h) {
            height = h;
        }

        public void getArea() {
            System.out.println(width * height);
        }

    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(3);
        rectangle.setWidth(5);
        rectangle.getArea();
    }
}
