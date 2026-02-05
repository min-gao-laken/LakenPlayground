package winter.lab1.practice02;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public class Practice02 {
    public static void main(String[] args) {
        show(new Cat());  // 以 Cat 对象调用 show 方法
        show(new Dog());  // 以 Dog 对象调用 show 方法

        Animal a1 = new Cat();  // 向上转型
        a1.eat();               // 调用的是 Cat 的 eat
        a1.work();              // 调用的是 Cat 的 work

        Cat c1 = (Cat) a1; // 向下转型
        c1.eat();
        c1.work();

    }

    public static void show(Animal a) {
        a.eat(); // 这里为啥能正确调用到对应的class
        if (a instanceof Cat) {
            Cat c = (Cat) a;
            c.eat();
            c.catchMouse();
            c.animalName();
        } else if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.eat();
            d.watchHouse();
            d.animalName();
        }
        /**
         * a.eat() 能跑对，靠的是 JVM 的动态绑定机制（运行时根据对象头信息去找真正的类）。
         *
         * instanceof 和向下转型，是为了开启子类的专属技能包，而不仅仅是重复调用那个共有的方法。
         *
         * 这种设计让程序既有“统一管理”的便利（比如一个 Animal 数组存下所有宠物），又能保留“个性化操作”的可能。
         */
    }
}





