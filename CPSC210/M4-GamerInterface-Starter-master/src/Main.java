import Model.Child;
import Model.Student;
import Model.Gamer;
import Model.Learner;

public class Main {
    public static void main(String[] args) {
        Gamer p1 = new Child();
        p1.learn();

        Learner p2 = new Student();
        p2.learn();

        p2 = new Child();
        p2.learn();
    }
}