package ca.ubc.cpsc210.taskmanager.model;

public abstract class WorkUnit {

	/**
	 * Constructor
	 */
	public WorkUnit() {
		
	}

	/**
	 * @return number of hours needed to complete this work unit
	 */
	public abstract int hoursToComplete();
}
