package de.hhu.propra16.tddtrainer.logic;

import de.hhu.propra16.tddtrainer.executor.ExecutionResult;

/**
 * Contains, whether the {@link Phase} is valid for the current {@link de.hhu.propra16.tddtrainer.catalog.Exercise} or not, the current {@link Phase} and {@link ExecutionResult}. 
 * @author luisa
 *
 */
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
