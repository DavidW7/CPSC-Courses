package ca.ubc.cpsc210.quizbuilder.model.quiz;

import ca.ubc.cpsc210.quizbuilder.model.exceptions.OutOfTriesException;
import ca.ubc.cpsc210.quizbuilder.model.exceptions.RetryAnswerException;
import ca.ubc.cpsc210.quizbuilder.model.questionslist.QuestionsList;

public class DecrementMarksQuiz extends EachAnswerMustBeRightQuiz {
    private int currentDecrement;

    public DecrementMarksQuiz(QuestionsList questions) {
        super(questions);
        currentDecrement = 0;
    }

    // MODIFIES: this
    // EFFECTS: submit an answer to the current question and return feedback string;
    // throws RetryAnswerException if the user should re-try the question;
    // throws OutOfTriesException if user has used up all attempts to answer the question.
    @Override
    public String submitAnswer(String answer) throws RetryAnswerException, OutOfTriesException {
        boolean correct = checkAnswer(answer);
        if (!correct) {
            currentDecrement++;
            if (currentDecrement >= curQuestion.getMaxMark()) {
                currentDecrement = 0;
                throw new OutOfTriesException("Out of tries for this question");
            }
            throw new RetryAnswerException("Wrong answer, max score decremented by total of " + currentDecrement + ", please retry.");
        }
        return "";
    }

    // MODIFIES: this
    // EFFECTS: checks the answer to the current question, updates marks earned and
    // returns true if the answer is correct, and false otherwise.
    @Override
    public boolean checkAnswer(String answer) {
        if (this.curQuestion.isCorrect(answer)) {
            markSoFar += curQuestion.getMaxMark() - currentDecrement;
            currentDecrement = 0;
            return true;
        }
        return false;
    }
}
