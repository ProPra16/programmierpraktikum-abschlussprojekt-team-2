package de.hhu.propra16.tddtrainer.logic;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.executor.ExecutionResult;
import de.hhu.propra16.tddtrainer.executor.Executor;

public class PhaseManager implements PhaseManagerIF {
	
	private Phase phase = Phase.RED;

	@Override
	public PhaseStatus checkPhase(Exercise exercise, boolean continuePhase) {
		ExecutionResult executionResult = new Executor().execute(exercise);

		if(phase.equals(Phase.RED)) {
			if((executionResult.getCompilerResult().hasCompileErrors()) ||
				(executionResult.getTestResult().getNumberOfFailedTests() == 1)) {
				if(continuePhase) {
					phase = Phase.GREEN;
				}
				return new PhaseStatus(true, executionResult);
			}
			else {
				return new PhaseStatus(false, executionResult);
			}
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
				return new PhaseStatus(true, executionResult);
			}
			else {
				return new PhaseStatus(false, executionResult);
			}
		}
	}

	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public Exercise resetPhase() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
