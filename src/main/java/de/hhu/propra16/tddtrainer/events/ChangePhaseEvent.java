package de.hhu.propra16.tddtrainer.events;

import de.hhu.propra16.tddtrainer.logic.Phase;

public class ChangePhaseEvent {
	
	private Phase phase;
	
	public ChangePhaseEvent(Phase phase) {
		this.phase = phase;
	}
	
	public Phase getPhase() {
		return phase;
	}

}
