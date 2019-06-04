# Food Services Card
:arrow_forward: Assignment Starter Code

Welcome to this assignment! :sparkles:

Please consult the course homepage for deadlines.



---

### Do NOT clone `A1-FoodServicesCard-Starter` repository! :warning:

* A copy of this repository is made for you in your GitHub account. 
* The copied repository will appear as `assign1_teamxxx` (where `xxx` is some number). 
* Using IntelliJ, clone that repository and open it. 

---



#### Table of Context:

  * [Overview](#overview)

  * [Task 1](#task-1) 

  * [Task 2](#task-2)

  * [Task 3](#task-3) 

  * [Submission](#submission)

    

---

### Overview


#### Learning Goals

- given the specification of a data abstraction:
  - test the abstraction using jUnit
  - implement the abstraction
- apply the principle of *single point of control* when designing tests and implementing the abstraction.



#### Description 

* We assume that we are designing an application that allows members of an organization (such as a business or university) to use a food services card to pay for food purchased at that organization's outlets. 
* A food services card must be preloaded before it can be used. 
* The card must have sufficient credit to cover the entire cost of a purchase, otherwise the purchase cannot be made. 
* To encourage members to use their card, the organization has a **rewards program** in place. 
  * Currently, `1` reward point is earned for **each cent** in purchases charged to the card. 
  * When at least `2000` reward points have been accumulated, those points are deducted from the account and a **cash-back** reward of `10` cents is added to the card. 
  * In other words, for each `$20` spent, a cash-back reward of `10` cents is earned. 
* Note that the number of points earned for each cent in purchases, the minimum number of reward points needed and the size of the cash-back reward could all change at a future date.



---

### Task 1

Carefully study the specification of the `FoodServicesCard` class.

  

##### Notes:

1. The declaration of the constants in the `FoodServiceCard` class is unusual. For example: 

   ```java
   public static int POINTS_NEEDED_FOR_CASH_BACK = 2000;
   ```

   Normally, this constant would be declared using the `final` keyword as follows: 

   ```java
   public static final int POINTS_NEEDED_FOR_CASH_BACK = 2000;
   ```

   The reason we have omitted this keyword is to allow us to run additional tests on your code to check that it correctly handles different values for these constants.

2. All monetary values are represented in cents. So, for example, $1.45 is represented as 145 cents. We do this to avoid having to use non-integer values in this assignment, in part because computations on non-integer values in Java are not exact, in general. 

3. When the `/` operator is applied to two values of type `int` the result is a value of type `int`. Specifically, the quotient (the integer part of the division) is produced. So, for example, `19 / 5` evaluates to `3`, as `5` divides into `19` three times (so the quotient is `3`) with remainder `4`. Note that we do not round up to the nearest integer!

   

---

### Task 2

Add unit tests to the `FoodServicesCardTest` class that test the methods in `FoodServicesCard` class.



##### Notes:

1. a `FoodServicesCardTest` class has been provided for you in the `tests` package. This class provides a template for the tests that you will write.

2. it is not necessary to write tests to explicitly check the implementation of simple getter and setter methods like `getBalance` and `getRewardPoints` but you must carefully test the implementation of all other methods. Also note that you will very likely make calls to `getBalance` and `getRewardPoints` when implementing tests for other methods, so these methods will be tested implicitly.

3. Keep in mind the principle of **Single Point of Control** when designing your tests. In other words, if we were to make a change to one of the constants provided for you in the `FoodServicesCard` class, you should not have to make any changes to your tests.

4. You may name your test methods as you wish (the `@Test` declaration in your testing class will ensure that each test is run). But, ensure that your tests have complete code coverage. That is, every method, every outcome of a conditional `if` statement, running a loop versus skipping the loop, etc. should be run in your tests.

When you have finished designing your tests, commit your changes and push your code to the remote repository on GitHub.  By doing this, you are making a backup of your work on GitHub in case something goes wrong in the upcoming tasks (or if you lose your laptop).



---

### Task 3

Implement the constructor and each of the methods in the `FoodServicesCard` class - again, keep in mind the principle of *Single Point of Control*. 

Run your tests and debug until all tests pass.  

Finally, run your tests with code coverage. (See [IntelliJ Documentation](https://www.jetbrains.com/help/idea/running-with-coverage.html) for how to run with code coverage, and how to see coverage results.)

Even though you have not explicitly written tests to check the `getBalance` and `getRewards` methods, you should have `100%` code coverage for this particular class, as these methods will certainly have been called when designing other tests. 



---

### Submission

Commit your code to your local repository and push it to the remote repository on GitHub.

Wait 2 minutes and then request the grade for your submission by entering the following as a comment on your commit.
```
@autobot #assign1
```
You'll be able to do this every 10 minutes. 
