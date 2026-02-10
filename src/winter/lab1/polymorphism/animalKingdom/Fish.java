package winter.lab1.polymorphism.animalKingdom;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public class Fish extends Animal implements Noisy{
    public Fish() {
    }

    public Fish(String name) {
        super(name);
    }

    public void makeNoise() {
        System.out.println("Blub blub blub");
    }
}
