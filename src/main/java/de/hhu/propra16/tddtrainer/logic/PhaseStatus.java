package de.hhu.propra16.tddtrainer.logic;

import de.hhu.propra16.tddtrainer.executor.ExecutionResult;

public class PhaseStatus {
	
	private boolean valid;
	private Phase phase;
	private ExecutionResult executionResult;
	
	public PhaseStatus(boolean valid, ExecutionResult executionResult, Phase phase) {
		this.valid = valid;
		this.executionResult = executionResult;
		this.phase = phase;
	}

	public boolean isValid() {
		return valid;
	}
	
	public Phase getPhase() {
		return phase;
	}

	public ExecutionResult getExecutionResult() {
		return executionResult;
	}	
}
