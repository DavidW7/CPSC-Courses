package ca.ubc.cs.cpsc210.management;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a department at a software development
 * company that has an arbitrary number of employees.
 */
public class Department {
	private Set<Employee> employees;
	private String name;
	
	/**
	 * Constructor
	 * 		Department has given name and no employees
	 * 
	 * @param name  the name of the department
	 */
	public Department(String name) {
		employees = new HashSet<Employee>();
		this.name = name;
	}
	
	/**
	 * Add an employee (no duplicates allowed) and set the
	 * employee's department to this department.
	 * 
	 * @param e   the employee to add to this department
	 */
	public void addEmployee(Employee e) {
		if (employees.add(e))
			e.setDepartment(this);		
	}
	
	/**
	 * If the department contains the specified employee, remove the employee 
	 * and disassociate employee with department.
	 * @param e  the employee to be removed from department
	 */
	public void removeEmployee(Employee e) {
		if (employees.contains(e)) {
			employees.remove(e);
			e.setDepartment(null);
		}
	}	
	
	/**
	 * Does department have a particular employee?
	 * @param e   employee to search for
	 * @return    true if dept has an employee with ID e.getEmployeeID()
	 */
	public boolean contains(Employee e) {
		return employees.contains(e);
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Get number of employees in this department
	 * @return  number of employees
	 */
	public int getNumEmployees() {
		return employees.size();
	}
}
