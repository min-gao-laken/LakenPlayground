package winter.lab1.derivation;

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
        // 相同的名字 不同的参数类型
        System.out.println("Person: " + p1);
        System.out.println("Employee: " + el);
    }
}

// + public
// - private
// 井号键 protected: 作用域 scope

// 一个class 只能有1个parent class