package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-03
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Design a class Item with fields:
 * - name
 * - price
 * 2. Add methods to:
 * - update the price safely
 * - display the item
 * 3. Create several Item objects and print their properties.
 * 4. Think about how these Item objects could be used in a
 * larger system such as an inventory or menu.
 * ------------------------------------------------------------
 */
public class Chapter12_ModelingRealSystems {
    static class Item {
        private String name;
        private double price;

        public Item(String name, double price) {
            if (price > 0) {
                this.price = price;
            } else {
                this.price = 0;
            }
            this.name = name;
        }

//        public void updatePrice(double price) {
//            if (price > 0) {
//                this.price = price;
//            }
//        }

        public void setPrice(double price) {
            if (price > 0) {
                this.price = price;
            }
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        Item i1 = new Item("book", 11);
        Item i2 = new Item("pen", 12);
        System.out.println(i1.getName() + "_" + i1.getPrice());
        i1.setPrice(21);
        System.out.println(i1.getName() + "_" + i1.getPrice());
        System.out.println(i2.getName() + "_" + i2.getPrice());

    }
}
