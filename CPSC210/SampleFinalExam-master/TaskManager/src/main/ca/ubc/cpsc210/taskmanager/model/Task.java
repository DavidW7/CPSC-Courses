package ca.ubc.cpsc210.taskmanager.model;

public class Task extends WorkUnit {
	
	private int hours;
	
	/**
	 * Constructor
	 * @param hours  hours needed to complete this task
	 */
	public Task(int hours) {
		this.hours = hours;
	}

	/*
	Getter for hours
	 */
	@Override
	public int hoursToComplete() {
		return hours;
	}
}
