package winter.lab1.interfacesinclassb;

import java.util.Arrays;

public class InterfacesInClassB {
    public static void main(String[] args) {
        Widget[] widgets = new Widget[3];
        widgets[0] = new Widget(1001, "Screw", 47);
        widgets[1] = new Widget(1006, "Bolt", 32);
        widgets[2] = new Widget(1003, "Zebra", 3);

        // 1. print out the widgets before sorting the array
        System.out.println("------Before sort------");
        for (int i = 0; i < widgets.length; i++) {
            System.out.println(widgets[i]);
        }

        // 2. sort the array
        Arrays.sort(widgets);

        // 3. print out the widgets after sorting the array
        System.out.println("------After sort------");
        for (int i = 0; i < widgets.length; i++) {
            System.out.println(widgets[i]);
        }

        System.out.println(widgets[0].compareTo(widgets[1]));
        System.out.println(widgets[1].compareTo(widgets[2]));
    }
}
