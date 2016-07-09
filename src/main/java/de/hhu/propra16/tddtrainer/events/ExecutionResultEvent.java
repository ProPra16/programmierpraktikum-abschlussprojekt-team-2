package de.hhu.propra16.tddtrainer.events;

import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public class ExecutionResultEvent {

	private PhaseStatus phaseStatus;
	
	public ExecutionResultEvent(PhaseStatus phaseStatus) {
		this.phaseStatus = phaseStatus;
	}
	
	public PhaseStatus getPhaseStatus() {
		return phaseStatus;
	}
	
}
