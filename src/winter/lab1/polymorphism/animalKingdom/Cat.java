package winter.lab1.polymorphism.animalKingdom;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public class Cat extends Animal {
    public Cat() {
    }

    public Cat(String name) {
        super(name);
    }

//    @Override
    public void makeNoise() {
        System.out.println(getName() + ": Meow");
    }

    public void purr() {
        System.out.println(getName() + ": PRRRR______Cat");
    }
}
