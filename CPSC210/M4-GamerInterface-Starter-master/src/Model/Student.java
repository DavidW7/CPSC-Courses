package Model;

public class Student implements Learner {
    @Override
    public void learn() {
        System.out.println("I'm learning");
    }

    @Override
    public void play() {
        System.out.println("I'm playing");

    }
}
