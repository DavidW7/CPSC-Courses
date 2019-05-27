package ca.ubc.cpsc210.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

public class Project extends WorkUnit {

	List<WorkUnit> workUnits;

	public Project() {
		workUnits = new ArrayList<>();
	}

	public void add(WorkUnit wu) {
		workUnits.add(wu);
	}

	@Override
	public int hoursToComplete() {
		int total = 0;
		for (WorkUnit wu : workUnits) {
			total += wu.hoursToComplete();
		}
		return total;
	}
}
