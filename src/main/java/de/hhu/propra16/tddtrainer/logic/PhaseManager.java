package de.hhu.propra16.tddtrainer.logic;

import com.google.common.eventbus.EventBus;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.events.ExerciseEvent;
import de.hhu.propra16.tddtrainer.executor.*;
import de.hhu.propra16.tddtrainer.gui.catalog.ExerciseSelector;
import de.hhu.propra16.tddtrainer.tracking.TrackingManager;
import vk.core.api.CompileError;

public class PhaseManager implements PhaseManagerIF {
	
	private Phase phase = Phase.RED;
	private Exercise validExercise;
	private TrackingManager trackingManager;
	private EventBus bus;
	private ExerciseSelector exerciseSelector;
	
	public PhaseManager(TrackingManager trackingManager, ExerciseSelector exerciseSelector, EventBus bus) {
		this.trackingManager = trackingManager;
		this.bus = bus;
		this.exerciseSelector = exerciseSelector;
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
	public void resetPhase() {
		if(phase.equals(Phase.REFACTOR)) {
			throw new IllegalStateException("Reset not permitted during Refactor.");
		}
		if(phase.equals(Phase.GREEN)) {
			phase = Phase.RED;
		}
		
		bus.post(new ExerciseEvent(validExercise));
	}
	
	public void selectExercise() {
		validExercise = exerciseSelector.selectExercise();
		
		if(validExercise != null) {
			bus.post(new ExerciseEvent(validExercise));
		}
	}
}
