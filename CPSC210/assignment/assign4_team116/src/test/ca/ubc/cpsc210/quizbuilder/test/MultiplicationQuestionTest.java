package ca.ubc.cpsc210.quizbuilder.test;

import ca.ubc.cpsc210.quizbuilder.model.question.MultiplicationQuestion;
import ca.ubc.cpsc210.quizbuilder.model.question.Question;
import ca.ubc.cpsc210.quizbuilder.model.questionslist.QuestionsList;
import ca.ubc.cpsc210.quizbuilder.model.quiz.EachAnswerMustBeRightQuiz;
import ca.ubc.cpsc210.quizbuilder.model.quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationQuestionTest extends QuizBuilderTests {
    // feel free to modify this as needed
    private Question q1;
    private QuestionsList qList1;
    private Quiz quiz1;

    @BeforeEach
    public void runBefore() {
        q1 = new MultiplicationQuestion(8, 3, 4);
        qList1 = new QuestionsList();
        qList1.addQuestion(q1);
        quiz1 = new EachAnswerMustBeRightQuiz(qList1);
    }

    @Test
    public void testMultiplicationQuestionRightAnswer() {
        quiz1.startQuiz();
        quiz1.getNextQuestion();
        tryRightAnswer(quiz1, "12");
        quiz1.endQuiz();

        assertEquals(8, quiz1.getMarkSoFar());
    }
}