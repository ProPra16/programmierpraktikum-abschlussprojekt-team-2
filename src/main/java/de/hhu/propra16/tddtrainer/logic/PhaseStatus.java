package de.hhu.propra16.tddtrainer.logic;

import de.hhu.propra16.tddtrainer.executor.ExecutionResult;

public class PhaseStatus {
	
	private boolean valid;
	private ExecutionResult executionResult;
	
	public PhaseStatus(boolean valid, ExecutionResult executionResult) {
		this.valid = valid;
		this.executionResult = executionResult;
	}

	public boolean isValid() {
		return valid;
	}

	public ExecutionResult getExecutionResult() {
		return executionResult;
	}	
}
