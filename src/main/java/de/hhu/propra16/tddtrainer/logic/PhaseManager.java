package de.hhu.propra16.tddtrainer.logic;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.executor.*;
import de.hhu.propra16.tddtrainer.tracking.TrackingManager;
import vk.core.api.CompileError;

public class PhaseManager implements PhaseManagerIF {
	
	private Phase phase = Phase.RED;
	private Exercise validExercise;
	private TrackingManager trackingManager;
	
	public PhaseManager(TrackingManager trackingManager) {
		this.trackingManager = trackingManager;
	}

	@Override
	public PhaseStatus checkPhase(Exercise exercise, boolean continuePhase) {
		ExecutionResult executionResult = new Executor().execute(exercise);
		PhaseStatus phaseStatus;
		
		if(phase.equals(Phase.RED)) {
			boolean valid = true;
			if(executionResult.getCompilerResult().hasCompileErrors()) {
				loop:
				for(CompilationResult cr : executionResult.getCompileErrors()) {
					for(CompileError ce : cr.getCompileErrors()) {
						if(!(ce.getMessage().contains("cannot find symbol"))) {
							valid = false;
							break loop;
						}
					}
				}
			} 
			else if(executionResult.getTestResult().getNumberOfFailedTests() != 1) {
				valid = false;
			}
			if(valid) {
				if(continuePhase) {
					phase = Phase.GREEN;
				}
				validExercise = exercise;
			}
			phaseStatus = new PhaseStatus(valid, executionResult, phase);
		}
		
		else {
			if((!(executionResult.getCompilerResult().hasCompileErrors())) &&
				(executionResult.getTestResult().getNumberOfFailedTests() == 0)) {
				if(continuePhase) {
					if(phase.equals(Phase.GREEN)) {
						phase = Phase.REFACTOR;
					}
					else {
						phase = Phase.RED;
					}
				}
				validExercise = exercise;
				phaseStatus = new PhaseStatus(true, executionResult, phase);
			}
			else {
				phaseStatus = new PhaseStatus(false, executionResult, phase);
			}
		}
		trackingManager.track(exercise, phaseStatus);
		return phaseStatus;
	}

	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public Exercise resetPhase() {
		if(phase.equals(Phase.REFACTOR)) {
			throw new IllegalStateException("Reset not permitted during Refactor.");
		}
		if(phase.equals(Phase.GREEN)) {
			phase = Phase.RED;
		}
		return validExercise;
	}
}
