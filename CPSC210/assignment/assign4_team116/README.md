# Quiz Builder
:arrow_forward: Assignment Starter Code

Welcome to this assignment! :sparkles:

Please consult the course homepage for deadlines.



---

### Do NOT clone `A4-QuizBuilder-Starter` repository! :warning:

* A copy of this repository is made for you in your GitHub account. 
* The copied repository will appear as `assign4_teamxxx` (where `xxx` is some number). 
* Using IntelliJ, clone that repository and open it. 

---



#### Table of Context:

  * [Overview](#overview)

  * [How To Approach This Assignment](#how-to-approach-this-assignment) 

  * [Submission](#submission)

    

---

### Overview


#### Learning Goals

-   work with a type hierarchy
-   work with code that uses exception handling

#### Description 

You have been introduced to the concepts of exceptions and exceptional control flow. In this assignment you will practice your understanding of inheritance in Java and solidify your understanding of exceptions by extending an existing  _Quiz Builder_ application. The  _Quiz Builder_  application defines classes to represent a quiz that a student might take. In this assignment you will add a new type of question having to do with multiplication and add a new type of quiz. The new type of quiz requires the student taking the quiz to re-enter an answer, if they entered an incorrect answer, and also automatically deducts one from the mark for the question on each incorrect answer attempt. When the potential points have run out for a question, the system moves on to the next question.

#### Quiz Builder: Orientation

The  `QuizBuilder`  already implements two kinds of questions:  `ShortAnswerQuestion`  and  `TrueFalseQuestion`, and includes three types of quizzes:  `TimedQuiz`,  `InstantFeedbackQuiz`, and  `EachAnswerMustBeRightQuiz`. Look through these classes and understand how they operate at the high-level, as this will help you solve the two tasks in this assignment.

You can run the  `QuizBuilder`  application and interact with it from the console. The application will initially prompt you for the kind of quiz you want to take, and it will then show you two sample true/false questions.



---

### How To Approach This Assignment

#### Task 1

Create a new class called  `MultiplicationQuestion`  in the  `ca.ubc.cpsc210.quizbuilder.model.question`  package. The  `MultiplicationQuestion`  class must extend the  `Question`  class and represents questions of the form  _factor1 * factor2 = ???_.

For example:  `2 * 3 = ???`.

This class must implement the following constructor:  

```java
public MultiplicationQuestion(double maxMark, int factor1, int factor2)
```

You must also specify, test and implement any methods that must be overridden or added to provide the desired functionality. Note that a skeleton test class has been provided for you along with some useful helper methods in its superclass.

Hint: note the skeleton test class `MultiplicationQuestionTest` extends `QuizBuilderTests` and it has the following fields

```java
private Question q1;
private QuestionsList qList1;
private Quiz quiz1;
```

We want you to test `MultiplicationQuestion` in the process of running a quiz. So, you should for instance make a multiplication question, add it to a quiz and run it and test it with right answer, wrong answer, etc. 

Your code needs to work only for answer submissions that are integers. That is, you must assume that "6" is the right answer for the question above, while "6.0" is not. Further, your code must work properly (catching a  `NumberFormatException`) for the case when the user supplies an answer that is not an  `int`  (for instance, a number over 2147483647, or one containing a non-numeric character). In this case  `MultiplicationQuestion.isCorrect()`  should return false.

You can use the following method to convert a  `String`  object to an  `int`:

```java
int answerI = Integer.parseInt(answerS);
```

If you want to interactively test your new question class -- there are two commented-out lines in  `QuizRunner`  that create a new instance of  `MultiplicationQuestion`  and add it to the list of sample questions that you can answer when you run the program. These lines follow lines containing the comment:  `Un-comment next line to test MultiplicationQuestion`.

NOTE: AutoTest will not be able to compile (and therefore grade) your submission until your code contains a properly defined `MultiplicationQuestion` class  _and_  a `DecrementMarksQuiz` class (see Task 2). So there is no point in requesting a grade from AutoTest at this point.

#### Task 2

Create a new class, called  `DecrementMarksQuiz`, and add it to the  `ca.ubc.cpsc210.quizbuilder.model.quiz`  package. This class must extend  `EachAnswerMustBeRightQuiz`  and represents a new kind of quiz. If the user takes this quiz and they submit an incorrect answer they can retry the answer (just like with the  `EachAnswerMustBeRightQuiz`  quiz type), but their maximum mark for that question is decremented by one. They get as many tries as there are marks initially set for the question. When the user runs out of tries, the program should throw an  `OutOfTriesException`  (provided) that gracefully allows them to move on to the next question (but gives them no points for this one). You do not have to worry about catching the exception for our purposes -- you will see that it is already caught in  `QuizRunner`  and the in AutoTest tests. But in your own testing, you will need to handle the  `OutOfTriesException`, and also test that it is thrown at the correct time.

You must specify, test and implement a constructor and any methods that must be overridden or added to provide the desired functionality. Note that a skeleton test class has been provided for you along with some useful helper methods in its superclass.

If you want to interactively test your new quiz class there are a few commented-out lines in  `QuizRunner`  that create the new quiz type and allow you to try it out interactively when you run the program. To find these lines, look for the comments:  `Un-comment next ... line(s) to interactively test DecrementMarksQuiz`, and un-comment the line(s) below.

NOTE: Before you request a grade from AutoTest check that you have defined the `MultiplicationQuestion`  class and `DecrementMarksQuiz`  class in the right packages (as specified above).



---

### Submission

When you are ready, request feedback on your work by entering the following comment on your commit on GitHub:

```
@autobot #assign4
```

In this assignment, you will have to wait 8 hours between successive requests for feedback. Your grade is based  _solely_  on the number of our tests that pass when run against your implementation of the classes in the  `model`  package, so code coverage does not figure into your final grade.