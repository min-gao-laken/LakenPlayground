package winter.lab1.polymorphism.animalKingdom;

public interface Noisy {
    // all properties in an interface must be final (constant)
    public final boolean isNoisy = true;

    // all methods in an interface are abstract by default, so you
    // don't need the keyword "abstract"
    public void makeNoise();
}
