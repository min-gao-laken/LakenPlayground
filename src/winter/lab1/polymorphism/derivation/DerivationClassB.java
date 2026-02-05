package winter.lab1.polymorphism.derivation;

/**
 * @author Laken
 * @date 2026-01-27
 * @description
 */
public class DerivationClassB {
    public static void main(String[] args) {
        Person p1 = new Person("Bill", "Ba", 'x');
        Employee el = new Employee("Mike", "Va", 'J', 1001, "Instructor");
//        el.setFirstName("Mike");
//        el.setLastName("va");
//        el.setMiddleInit('J');

        // Override
        // Overload
        System.out.println("Person: " + p1);
        System.out.println("Employee: " + el);
    }
}

// + public
// - private
// protected: scope

// A class is allowed to extend only one parent class.