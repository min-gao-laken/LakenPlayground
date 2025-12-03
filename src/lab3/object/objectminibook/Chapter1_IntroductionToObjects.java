package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Add a toggle() method to the Light class that switches
 * the light to the opposite state.
 * 2. Create two Light objects.
 * 3. Turn one on, leave the other off, and print both states
 * to confirm that each object maintains its own state.
 * ------------------------------------------------------------
 */
public class Chapter1_IntroductionToObjects {
    static class Light {
        private boolean on;

        public void turnOn() {
            on = true;
        }

        public void turnOff() {
            on = false;
        }

        public boolean isOn() {
            return on;
        }

        public void toggle() {
            if (on) {
                turnOff();
            } else if (!on) {
                turnOn();
            }
        }
    }

    public static void main(String[] args) {
        Light light1 = new Light();
        Light light2 = new Light();

        light1.turnOn();

        System.out.println("light1: " + light1.isOn());
        System.out.println("light2: " + light2.isOn());

        light1.toggle();
        light2.toggle();
        System.out.println("light1 after toggle: " + light1.isOn());
        System.out.println("light2 after toggle: " + light2.isOn());

    }
}
