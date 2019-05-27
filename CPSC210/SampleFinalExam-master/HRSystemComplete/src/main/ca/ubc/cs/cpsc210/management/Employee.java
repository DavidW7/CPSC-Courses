package ca.ubc.cs.cpsc210.management;

/**
 * An employee at a software development company.
 */
public class Employee {
	private Department dept;
	private String name;
	private String employeeID;
	
	/**
	 * Constructor
	 * @param name         name of employee
	 * @param employeeID   ID of employee
	 */
	public Employee(String name, String employeeID) {
		this.name = name;
		this.employeeID = employeeID;
		dept = null;
	}
	
	/**
	 * Sets department for this employee. Removes employee from previous
	 * department, if any. If department is not null, adds employee to department.  
	 * 
	 * @param d  department to which employee is to be added
	 */
	public void setDepartment(Department d) {		
		if (dept != d) {
			if (dept != null) 
				dept.removeEmployee(this);
			
			dept = d;
			if (dept != null)
				dept.addEmployee(this);
		}
	}
	
	public Department getDepartment() {
		return dept;
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

        return employeeID != null ? employeeID.equals(employee.employeeID) : employee.employeeID == null;
    }

    @Override
    public int hashCode() {
        return employeeID != null ? employeeID.hashCode() : 0;
    }
}
