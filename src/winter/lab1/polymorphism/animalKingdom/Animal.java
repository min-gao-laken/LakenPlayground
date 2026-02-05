package winter.lab1.polymorphism.animalKingdom;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public abstract class Animal {
    String name;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
        System.out.println(name); // call super()每次都会调用 TODO
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void makeNoise() {
//        System.out.println("I am an animal and my name is " + getName());
//    }

    public abstract void makeNoise();
}
