# Vending Machine

:arrow_forward: Assignment Starter Code

Welcome to this assignment! :sparkles:

Please consult the course homepage for deadlines.



---

### Do NOT clone `A5-VendingMAchine-Starter` repository! :warning:

* A copy of this repository is made for you in your GitHub account. 
* The copied repository will appear as `assign5_teamxxx` (where `xxx` is some number). 
* Using IntelliJ, clone that repository and open it. 

---



#### Table of Context:

  * [Overview](#overview)

  * [How To Approach This Assignment](#how-to-approach-this-assignment) 

  * [Submission](#submission)

    

---

### Overview


#### Learning Goals

- given the specification of a data abstraction:
    - test the abstraction using jUnit
    - implement the abstraction
    - use a `List` to represent data of arbitrary size

#### Description 

In this assignment you will work on a data abstraction that  represents part of a vending machine. The class that you will implement  is called `PaymentUnit`. It represents that part of a vending  machine that deals with payment and the calculation of change. We have  completed part of the design for you by providing a specification for each of the methods of this class.

Your task is to design tests for and implement those methods. You  will then submit your solution to AutoTest for grading where it will be  checked using tests that we have written. Your grade will be based on  the number of our tests that pass, the number of your tests that pass  and the degree to which your tests cover the implementation of the `PaymentUnit` class.

Note: In this assignment, you will have to wait six (6) hours  between successive requests to AutoTest. It is therefore very important  that you take time to design your own tests to remove as many bugs as you can before you submit to AutoTest!



#### Resources

Familiarize yourself with Java's [Enum types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)  if you have not done so already - see the section on the `Day` enumeration only - skip the section on the `Planet` enumeration.



#### Vending Machine Properties

The vending machine is meant to work like this:

1. After construction, we can optionally load it up with coins that are "banked" and are then used to give change when needed.

2. To start a transaction, coins are inserted into the machine.

3. When a purchase for a particular amount is made, the coins inserted  to make the purchase are added to the coins "banked" in the machine.  They can then be used to give change when needed.

4. Correct change is returned to the customer, if it's available. Note: It is not just the right *amount* that is returned - but the right *coins*!  When change is figured out, the highest value coins available are used  first. So, if 35 cents change is due, this will be delivered as 1  quarter plus 1 dime, rather than 3 dimes and 1 nickel. However, if there are no quarters available, 3 dimes and 1 nickel will be returned. If  there are no dimes available either, 7 nickels will be returned  (assuming there are that many available in the machine). 

   >  As a  consequence of using larger value coins first, there are some cases  where full change will not be given. For example, in the case where 40  cents in change is due and the machine has 1 quarter and 4 dimes  available, given that larger value coins are used first, the machine  will produce 1 quarter and 1 dime in change (not 4 dimes) - the customer  will forfeit the remaining 5 cents.  
   >
   > Note that, in general, full change is not guaranteed - the machine will return only as many coins as are available.

5. When a transaction is cancelled before purchase, the *same* coins inserted so far are returned to the customer.



---

### How To Approach This Assignment

1. Familiarize yourself with the `Coin` enumeration. Note  that this is just like an enumeration seen in CPSC 110. It consists of a  number of distinct values, each of which represents one of the coins:  loonie, quarter, dime and nickel. Note that there's a method that  produces the value of a coin. Do not make any changes to this code!
2. Familiarize yourself with the specification of each of the methods in the `PaymentUnit` class.
3. Design tests for the `PaymentUnit` class. Note that a `PaymentUnitTest` class has been provided for you.
4. Implement the `PaymentUnit` constructor. Be sure to  familiarize yourself with the given fields - do not change them! Run  your tests and check that any that you expect to pass at this point, do in fact pass. Debug as needed before moving on.
5. Implement the methods associated with banking coins: `clearCoinsBanked`, `addToCoinsBanked`, `getNumberOfCoinsBankedOfType`, `getValueOfCoinsBanked`. Again, run your tests and check that any you expect to pass at this point, do in fact pass. Debug as needed before moving on.
6. Implement the methods associated with inserting coins and cancelling a transaction: `insertCoin`, `getValueOfCoinsInserted`, `cancelTransaction`. Run your tests and debug as necessary.
7. Implement the `makePurchase` method. Run your tests and debug until all tests pass.
8. Finally, run your tests with coverage and ensure that you have 100% coverage of the methods in the `PaymentUnit` class.

**Note:** in addition to the specified methods, you may wish to design helper methods. Remember the "one function, one task" rule? It applies here too!



---

### Submission

When you are confident that you have a correct solution, you should  submit to AutoTest by entering the following comment on your commit: 

```
@autobot #assign5
```

When you submit, AutoTest will run our tests (there are nearly 30 of them) and yours. Remember that your grade is based on the degree to which your tests cover your implementation of the `PaymentUnit` class (20%) and the number of our tests that pass (80%).  For this assignment, you will have to wait 6 hours between successive requests for feedback from AutoTest. 