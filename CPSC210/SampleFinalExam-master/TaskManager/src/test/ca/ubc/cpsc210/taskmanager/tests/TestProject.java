package ca.ubc.cpsc210.taskmanager.tests;

import ca.ubc.cpsc210.taskmanager.model.Project;
import ca.ubc.cpsc210.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject {
	
	private Project prj;

	@BeforeEach
	public void setUp() {
		prj = new Project();
	}

	@Test
	public void testEmpty() {
		assertEquals(0, prj.hoursToComplete());
	}
	
	@Test
	public void testOneTask() {
		prj.add(new Task(4));
		assertEquals(4, prj.hoursToComplete());
	}
	
	@Test
	public void testManyTasks() {
		prj.add(new Task(7));
		prj.add(new Task(2));
		prj.add(new Task(4));
		assertEquals(13, prj.hoursToComplete());
	}
	
	@Test
	public void testSubProject() {
		Project subPrj1 = new Project();
		subPrj1.add(new Task(4));
		subPrj1.add(new Task(5));
		prj.add(new Task(7));
		prj.add(subPrj1);
		prj.add(new Task(2));
		
		Project subPrj2 = new Project();
		subPrj2.add(new Task(4));
		subPrj2.add(new Task(12));
		prj.add(subPrj2);
		
		assertEquals(34, prj.hoursToComplete());
	}
}
