package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-02
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class Temperature with a private field Celsius.
 * 2. Add methods:
 * - convertToFahrenheit()
 * - convertToKelvin()
 * - raise(double amount)     // increases temperature
 * - lower(double amount)     // decreases temperature
 * 3. Create a Temperature object and call all methods to verify
 * each behaviour works correctly.
 * ------------------------------------------------------------
 */
public class Chapter8_ObjectBehaviour {
    static class Temperature {
        private double celsius;

        public Temperature(double t) {
            this.celsius = t;
        }

        public double convertToFahrenheit() {
            return (celsius * 9 / 5) + 32;
        }

        public double convertToKelvin() {
            return celsius + 273.15;
        }

        public void raise(double amount) {
            celsius += amount;
        }

        public void lower(double amount) {
            celsius -= amount;
        }

        public double getCelsius() {
            return celsius;
        }
    }

    public static void main(String[] args) {
        Temperature temp = new Temperature(25.0);
        System.out.println("Initial Celsius: " + temp.getCelsius());

        System.out.println("Fahrenheit: " + temp.convertToFahrenheit());
        System.out.println("Kelvin: " + temp.convertToKelvin());

        temp.raise(5);
        System.out.println("After raise(5): " + temp.getCelsius());

        temp.lower(10);
        System.out.println("After lower(10): " + temp.getCelsius());
    }
}
