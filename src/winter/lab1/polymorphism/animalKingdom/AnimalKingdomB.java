package winter.lab1.polymorphism.animalKingdom;

/**
 * @author Laken
 * @date 2026-02-03
 * @description
 */
public class AnimalKingdomB {
    public static void main(String[] args) {
//        Animal a1 = new Animal("Elephant");
//        Cat c1 = new Cat("Tiger");
//        Dog d1 = new Dog("Wolf");
//
//        a1.makeNoise();
//        c1.makeNoise();
//        d1.makeNoise();


        Animal[] animals = new Animal[4];
//        animals[0] = new Animal("Elephant");
        animals[0] = new Fish("Bass");

        animals[1] = new Cat("Tiger");
        animals[2] = new Dog("Wolf");
        animals[3] = new Cat("Lion");

        Noisy[] noisyAnimal = new Noisy[4]; // todo
        noisyAnimal[0] = new Fish("Bass");
        noisyAnimal[1] = new Cat("Tiger");
        noisyAnimal[2] = new Dog("Wolf");
        noisyAnimal[3] = new Cat("Lion");


//        Animal a1 = new Animal("Elephant"); // 'Animal' is abstract; cannot be instantiated
        Animal a1 = new Fish("Koi"); // it works
//        a1.makeNoise();

        for (int i = 0; i < noisyAnimal.length; i++) {
            animals[i].makeNoise();
//            animals[i].purr(); it does not work

            if (animals[i] instanceof Cat) {
                Cat c1 = (Cat) animals[i];
                c1.purr();
            }

            if (animals[i] instanceof Dog d1) {
                d1.fetch();
            }
        }
    }
}
