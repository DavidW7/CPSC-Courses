package Model;

public class Child implements Gamer, Learner {
    @Override
    public void learn() {
        System.out.printf("Oh yeah");

    }

    @Override
    public void play() {
        System.out.println("Wooohooo");

    }

    @Override
    public void sleep() {
        System.out.println("Skurrttt");

    }
}
