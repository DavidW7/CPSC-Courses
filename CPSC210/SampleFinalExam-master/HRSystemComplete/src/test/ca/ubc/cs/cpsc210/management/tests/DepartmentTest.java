package ca.ubc.cs.cpsc210.management.tests;

import ca.ubc.cs.cpsc210.management.Department;
import ca.ubc.cs.cpsc210.management.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentTest {

	private Department deptA;
	private Department deptB;
	private Employee emplA;
	private Employee emplB;
	
	@BeforeEach
	public void runBefore() {
		deptA = new Department("Testing");
		deptB = new Department("Marketing");
		emplA = new Employee("Jo", "J90");
		emplB = new Employee("Pat", "C3P0");
	}
	
	@Test
	public void testConstructor() {
		assertEquals("Testing", deptA.getName());
		assertEquals(0, deptA.getNumEmployees());
	}
	
	@Test
	public void testAddEmployee() {
		deptA.addEmployee(emplA);
		
		assertTrue(deptA.contains(emplA));
		assertEquals(1, deptA.getNumEmployees());
		assertEquals(deptA, emplA.getDepartment());
	}
	
	@Test
	public void testAddEmployeeTwice() {
		deptA.addEmployee(emplA);
		deptA.addEmployee(emplA);
		
		assertTrue(deptA.contains(emplA));
		assertEquals(1, deptA.getNumEmployees());
		assertEquals(deptA, emplA.getDepartment());
	}
	
	@Test
	public void testAddMultipleEmployees() {
		deptA.addEmployee(emplA);
		deptA.addEmployee(emplB);
		
		assertTrue(deptA.contains(emplA));
		assertTrue(deptA.contains(emplB));
		assertEquals(2, deptA.getNumEmployees());
		assertEquals(deptA, emplA.getDepartment());
		assertEquals(deptA, emplB.getDepartment());
	}
	
	@Test
	public void testAddEmployeeAlreadyWithOtherDept() {
		deptA.addEmployee(emplA);
		deptB.addEmployee(emplA);
		
		assertFalse(deptA.contains(emplA));
		assertTrue(deptB.contains(emplA));
		assertEquals(deptB, emplA.getDepartment());
        assertEquals(0, deptA.getNumEmployees());
        assertEquals(1, deptB.getNumEmployees());
	}
	
	@Test
	public void testRemoveEmployee() {
		deptA.addEmployee(emplA);
		deptA.addEmployee(emplB);
		
		deptA.removeEmployee(emplA);
		
		assertTrue(deptA.contains(emplB));
		assertFalse(deptA.contains(emplA));
		assertEquals(1, deptA.getNumEmployees());
		assertEquals(null, emplA.getDepartment());
		assertEquals(deptA, emplB.getDepartment());
	}
	
	@Test
	public void testRemoveNotMember() {
		deptA.removeEmployee(emplA);
		
		assertEquals(0, deptA.getNumEmployees());
		assertEquals(null, emplA.getDepartment());
	}
	
	@Test
	public void testChangeEmployeeName() {
		deptA.addEmployee(emplA);
		emplA.setName("Joey");
		
		assertTrue(deptA.contains(emplA));
	}
}
