package winter.lab1.polymorphism.animalKingdom;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public class Dog extends Animal implements Noisy {
    public Dog() {
    }

    public Dog(String name) {
        super(name);
    }

    //    @Override
    public void makeNoise() {
        System.out.println(getName() + ": Bark");
    }

    public void fetch() {
        System.out.println(getName() + ": OHBOYGETTHISTICKIH______Dog");
    }
}
