# Helpdesk
:arrow_forward: Assignment Starter Code

Welcome to this assignment! :sparkles:

Please consult the course homepage for deadlines.



---

### Do NOT clone `A3-Helpdesk-Starter` repository! :warning:

* A copy of this repository is made for you in your GitHub account. 
* The copied repository will appear as `assign3_teamxxx` (where `xxx` is some number). 
* Using IntelliJ, clone that repository and open it. 

---



#### Table of Context:

  * [Overview](#overview)

  * [How To Approach This Assignment](#how-to-approach-this-assignment) 

  * [Submission](#submission)

    

---

### Overview


#### Learning Goals

- given the specification of a data abstraction that represents arbitrary sized information:

- - test the abstraction using jUnit
  - provide an implementation using a list, and appropriate branching and looping constructs

#### Description 

In this assignment you will specify, test and implement a data abstraction that represents a queue of incidents to be handled by a help-desk.  A queue is an example of a first-in, first-out data structure.  The first item inserted into the queue, is the first that will be removed.  This ensures that the first incident added to the queue, is the first one to be handled by the help-desk.  Items are maintained strictly in the order in which they are inserted into the queue.  We assume that the queue has a maximum size and, if full, no additional incidents can be added.

:warning:Note that you will have to wait 6 hours between each request for feedback from AutoTest, so don't leave this assignment to the last minute! :warning:



---

### How To Approach This Assignment

Note that a complete implementation of an `Incident` class has been provided for you.  You must not make any changes to this class.  Note that it is not necessary to design tests for this class - you can assume that its implementation is correct.

#### Specification

Add a specification for each of the methods described below to the `IncidentQueue` class in the `model` package.  Note that at this point you should write only a stub for each of these methods along with comments providing `REQUIRES`, `MODIFIES` and `EFFECTS` clauses, as appropriate. Be sure to strictly adhere to the signature of the methods provided - the name, return type and parameter list must be *exactly* as specified.  Note that a constant `MAX_SIZE` has been defined for you that represents the maximum number of items that can be in the queue at any one time.

- A constructor that takes no arguments. This method initializes each newly created `IncidentQueue` object as an empty queue.
- A method named `addIncident` that takes an `Incident` as an argument and, if the queue is not full, adds it to the end of the queue and returns true. If the queue is full, the method returns false. 
- A method named `getNextIncident` that takes no arguments and removes the `Incident` at the front of the queue and returns it.  It's assumed that when this method is called, the queue is not empty. 
- A method named `getPositionInQueueOfCaseNumber` that takes a case number (an `int`) as an argument and returns the position (an `int`) in the queue of the `Incident` with that case number.  If there is no incident in the queue with the given case number, the method must return the value -1.  Note that the first item in the queue has position 1, the second has position 2, etc.
- A method named `length` that takes no arguments and returns an `int` that represents the number of incidents currently in the queue.
- A method named `isEmpty` that takes no arguments and returns a `boolean`: true if the queue is empty, false otherwise
- A method named `isFull` that takes no arguments and returns a `boolean`: true if the queue is full, false otherwise

#### Testing

Now design unit tests for the methods that you have specified in the `IncidentQueue` class.  Recall that it is not necessary for you to test the `Incident` class.  Note that an empty `IncidentQueueTest` class has been provided for this purpose.  Add to the test class a field (call it whatever you want) to refer to the `IncidentQueueTest` object that your tests will be acting on and a method with the annotation `@BeforeEach` that creates an `IncidentQueue` object that can be used in each of the tests. Also add as many test methods (with the annotation `@Test`) as you think you need to adequately test the specified methods.  Remember that test methods must have a `void` return type and an empty parameter list. 

Complete the implementation of each of the tests in this class. When you are done, run the tests in the `IncidentQueueTest` class by right-clicking on that class in the Packages view and selecting *Run 'IncidentQueueTest'* from the drop-down menu.

At this point, some of your tests might pass because the stubs happen to produce the right value, but most of them should fail. That is ok, you haven't provided an implementation for the methods in the `IncidentQueue` class yet!

#### Implementation

Complete the implementation of the `IncidentQueue` class. You must use a field of type `LinkedList<Incident>` to hold the incidents as they are added to the queue, just as we used `ArrayList<Integer>` to hold the values in the `DataCollection`class that we talked about in class.  For now, don't worry about why we're using a `LinkedList` rather than an `ArrayList` - we'll get to that later in the course.  Take time to familiarize yourself with the operations available on a [LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) object - they will make the implementation of some of the methods in the `IncidentQueue` class fairly straightforward. 

Whenever you need to do something with each of the items in the list, you must use a for-each loop.

Once you have completed the implementation of the `IncidentQueue` class, run the tests in the `IncidentQueueTest` class and debug your code until all tests pass. Keep in mind that, if a test fails, the bug might be in your test rather than in the implementation of the method(s) called by that test.

When you get to the point that all of your tests pass, run your tests with code coverage to ensure that you have fully covered the code in the `IncidentQueue` class (do not include the Incident test in the measurement of code coverage as you were not required to design tests for this class).



---

### Submission

In this assignment, you will be allowed to request feedback from AutoTest an unlimited number of times but you will have to wait 6 hours between each request. It is therefore very important that you take time to design your tests with complete code coverage of the `IncidentQueue` class before you request feedback from AutoTest.

Be sure to remove any unused import statements before you push your work to GitHub.  You can automatically remove unused import statements by selecting **Code** → **Optimize Imports** from the IntelliJ menu.

You will need to enter the following comment on your commit to obtain AutoTest feedback:

```
@autobot #assign3
```

Your AutoTest score will comprise of a score from running our reference tests (weighted 80%) and a score from code coverage (weighted 20%).  The code coverage score will be determined from measures of line coverage and branch coverage.