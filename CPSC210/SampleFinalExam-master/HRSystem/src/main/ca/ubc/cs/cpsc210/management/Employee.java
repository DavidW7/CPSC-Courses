package ca.ubc.cs.cpsc210.management;

import java.util.Objects;

/**
 * An employee at a software development company.
 */
public class Employee {
	private String name;
	private String employeeID;
	private Department department;

	/**
	 * Constructor
	 * @param name         name of employee
	 * @param employeeID   ID of employee
	 */
	public Employee(String name, String employeeID) {
		this.name = name;
		this.employeeID = employeeID;
	}
	
	/**
	 * Sets department for this employee. Removes employee from previous
	 * department, if any. If department is not null, adds employee to department.  
	 * 
	 * @param d  department to which employee is to be added
	 */
	public void setDepartment(Department d) {
		if (department != null && d != department) {
			department.removeEmployee(this);
		}
		if (d != null) {
			this.department = d;
			d.addEmployee(this);
		} else {
			this.department = null;
		}
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmployeeID() {
		return employeeID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(employeeID, employee.employeeID);
	}

	@Override
	public int hashCode() {

		return Objects.hash(employeeID);
	}
}
