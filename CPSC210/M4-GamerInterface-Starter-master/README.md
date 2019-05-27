# Gamer Interface
:arrow_forward: Lecture Lab Starter Code

---

Welcome to this Lecture Lab :sparkles:

---

:heavy_check_mark: Task 1:

1. Using IntelliJ, clone this repository and open it.
2. There is only one class in the source folder: `Main`
3. Notice some _types_ are missing from the project, namely `Gamer` and `Learner` 

---

✔️ Task 2:

1. Make interfaces: `Gamer` and `Learner`
2. Give `Gamer` two methods: `play` and `sleep`
3. Give `Learner` two methods: `learn` and `play`

----

✔️ Task 3:

Make a class, `Student`, that implements `Learner`.  Use simple print statements (e.g. `I am learning!`) to implement the necessary methods.

---

✔️ Task 4:

Make a class, `Child`, that implements `Gamer` and `Learner`.  Implement the necessary methods using simple print statement that reflect that this is a child doing things.  

> How many methods do you need? 

---

✔️ Task 5:

Now visit the `Main` class and answer the following questions: 

1. Why doesn’t `p1.learn` compile even though the `Child` object definitely has an implementation for the `learn` method?
2. What is printed when we call `p2.learn` the first time?  Why?
3. What is printed when we call `p2.learn` the second time? Why?
4. `Gamer` and `Learner` have methods in common -- what did you need to do in `Child` to implement both interfaces?