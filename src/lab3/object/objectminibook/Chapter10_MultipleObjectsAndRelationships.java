package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-02
 * @description ------------------------------------------------------------
 * 4. TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class Box with width, height, and depth.
 * 2. Add a method getVolume() that computes width * height * depth.
 * 3. Create three Box objects with different sizes.
 * 4. Print their volumes and determine which Box has the largest
 * volume by comparing them manually using the results.
 * ------------------------------------------------------------
 */
public class Chapter10_MultipleObjectsAndRelationships {
    static class Box {
        private double width;
        private double height;
        private double depth;

        public double getVolume() {
            return width * height * depth;
        }

        public Box(double width, double height, double depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

    }

    public static void main(String[] args) {
        Box b1 = new Box(2, 3, 4);
        Box b2 = new Box(20, 3, 4);
        Box b3 = new Box(200, 3, 4);

        System.out.println("b1: " + b1.getVolume());
        System.out.println("b2: " + b2.getVolume());
        System.out.println("b3: " + b3.getVolume());

        System.out.println("Box b3 has the largest volume");
    }
}
