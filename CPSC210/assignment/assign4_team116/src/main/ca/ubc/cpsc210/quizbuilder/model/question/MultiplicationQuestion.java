package ca.ubc.cpsc210.quizbuilder.model.question;

public class MultiplicationQuestion extends Question {

    private int factor1;
    private int factor2;

    public MultiplicationQuestion(double maxMark, int factor1, int factor2) {
        super(maxMark, factor1 + " x " + factor2 + "= ???");
        this.factor1 = factor1;
        this.factor2 = factor2;
    }

    // EFFECTS: return true if correct answer, false otherwise
    //          if answer cannot be converted to integer, return false
    @Override
    public boolean isCorrect(String answer) {
        int product;

        try {
            product = Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return false;
        }

        return product == factor1*factor2;
    }
}
