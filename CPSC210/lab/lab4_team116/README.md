# Resource Finder XML

:arrow_forward: Lab Starter Code

Welcome to this Lab :sparkles:

Please consult the course homepage for deadlines.



----
### Do NOT clone `L4-ResourceFinderXML-Starter` repository! :warning:

* A copy of this repository is made for you in your GitHub account. 
* The copied repository will appear as `lab4_teamxxx` (where `xxx` is some number). 
* Using IntelliJ, clone that repository and open it. 

---



#### Table of Context:

- [Overview](#overview)

- [Task 1](#task-1) 

- [Task 2](#task-2)

- [Task 3](#task-3) 

- [Task 4](#task-4) 

- [Task 5](#task-5) 

- [Task 6](#task-6) 

- [Submission](#submission)

  

---
### Overview


#### Learning Goals

- parse data in XML format
- design unit tests for methods that throw exceptions
- implement methods with exception handling



:mega: In this lab you will continue to work on the Resource Finder application that you started in the last lab. Specifically, you will test and implement a parser for resource data stored in XML format.



:mega: You may notice that the `model` package appears not to be present. Don't worry about this - we've included the classes in this package in a different way :sunglasses:, so that this lab is not dependent on you having successfully completed the previous lab. As you work through this lab, you will therefore want to have the code from Lab 3 open in a separate window so you can refer to the specification of the classes in that package.



---

### Task 1

From *Project* view in IntelliJ, open the file `resources.xml` from the `data` folder. Study the structure of data in the file and notice that a number of resources are represented. In this lab, you will complete an XML *parser* whose role is to take the XML data and break it down into its component parts. So, in this particular case, we want to break the data down into individual resources, each of which has a name, address, location, etc. Some of these elements can be further broken down into their component parts. The location, for example, can be broken down into latitude and longitude components.

Read through the [W3Schools tutorial](http://www.w3schools.com/xml/xml_syntax.asp) to learn about XML syntax - do not read any of the other parts of the tutorial, as they are not relevant to parsing XML data using Java.



---

### Task 2

Clone the [L4-XMLParserExample](https://github.ugrad.cs.ubc.ca/CPSC210-2018S-T2/L4-XMLParserExample) project from GitHub. This provides a very basic example of XML parsing in action. Familiarize yourself with the data in the file `library.xml` -- you will need to switch to *Project* view to find this file.  Please do not proceed until you fully understand how the provided code parses the data in the `library.xml` file.



---


### Task 3
Complete the unit tests for the `XMLResourceParser.parse` method for the case where data is *successfully* parsed from the given `resources.xml` file (so no exceptions are expected when the method is called). Note that a `ResourceParserTest` class has been provided for you in the `tests.parser` package - look for the `TODO` comments to determine the methods that must be completed.

*Be sure to carefully read the specification for the parse method provided in the `IResourceParser` interface.*



---

### Task 4

We will now work on the `ResourceHandler` class. Note that the role of this class is to specify what should be done when the parser encounters the start or end of the document, or the start or end of an element, etc.

It is recommended that you approach the implementation of the `ResourceHandler` class in two passes as follows:

**First pass:** implement the `ResourceHandler` class so that it works as specified when the XML file to be parsed has the required format and is missing no data (so no exceptions are expected to be raised). Use the sample XML parser provided as a guide. Check your implementation by running the tests in the `ResouceParserTest` class and ensuring that they all pass.  

**Second pass:** modify your implementation so that it handles the exceptional cases, for example, when data is missing from the XML file.  Note that you will also have to complete the implementation of the `XMLResourceParser.parse` method so that it adheres to its specification (see the `IResourceParser` interface).  Then run all your tests and debug until they pass.



---

### Task 5

Run the application. Check that 9 markers have been plotted on the map to represent the 9 resources parsed from the XML file. Click a marker and verify that the corresponding resource information appears in a pop-up window. You can dismiss the pop-up by clicking it. Try filtering the resources by selecting various services - verify that the *all of* and *any of* options work correctly. 



---

### Task 6

Research another social service available in Vancouver and add it to the `resources.xml` file. Run the application and verify that your resource is marked on the map and that the corresponding contact information and services appear in the pop-up menu when the marker is clicked.



---

### Submission

Submit your code to AutoTest by entering the following comment on your commit: 

```
@autobot #lab4
```

AutoTest will run a set of reference tests against your code. Your score will be based *solely* on the number of our tests that pass, so code coverage does not comprise part of your AutoTest grade on this lab. 