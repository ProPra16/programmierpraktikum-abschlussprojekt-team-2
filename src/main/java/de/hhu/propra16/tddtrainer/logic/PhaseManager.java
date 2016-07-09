package de.hhu.propra16.tddtrainer.logic;

import com.google.common.eventbus.EventBus;

import de.hhu.propra16.tddtrainer.babysteps.BabystepsManager;
import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.events.ExecutionResultEvent;
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
	private BabystepsManager babystepsManager;
	private Exercise originalExercise;
	
	public PhaseManager(TrackingManager trackingManager, ExerciseSelector exerciseSelector, EventBus bus) {
		this.originalExercise = new Exercise();
		this.trackingManager = trackingManager;
		this.bus = bus;
		this.exerciseSelector = exerciseSelector;
		this.babystepsManager = new BabystepsManager(this);
	}

	@Override
	public PhaseStatus checkPhase(Exercise exercise, boolean continuePhase) {
		ExecutionResult executionResult = new Executor().execute(exercise);
		PhaseStatus phaseStatus;
		
		if(phase == Phase.RED) {
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
					babystepsManager.start(originalExercise.getBabyStepsCodeTime());
				}
				validExercise = exercise;
			}
			phaseStatus = new PhaseStatus(valid, executionResult, phase);
		}
		
		else {
			if((!(executionResult.getCompilerResult().hasCompileErrors())) &&
				(executionResult.getTestResult().getNumberOfFailedTests() == 0)) {
				if(continuePhase) {
					if(phase == Phase.GREEN) {
						phase = Phase.REFACTOR;
						babystepsManager.stop();
					}
					else {
						phase = Phase.RED;
						babystepsManager.start(originalExercise.getBabyStepsTestTime());
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
		bus.post(new ExecutionResultEvent(executionResult));
		return phaseStatus;
	}

	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public void resetPhase() {
		if(phase == Phase.REFACTOR) {
			throw new IllegalStateException("Reset not permitted during Refactor.");
		}
		if(phase == Phase.GREEN) {
			phase = Phase.RED;
		}
		
		if(validExercise != null) {
			babystepsManager.start(originalExercise.getBabyStepsTestTime());
			bus.post(new ExerciseEvent(validExercise));
		}
	}
	
	public void selectExercise() {
		Exercise exercise = exerciseSelector.selectExercise();
		if(exercise == null) {
			return;
		}
		if(exercise.isBabyStepsActivated()) {
			babystepsManager.enable();
		}
		else {
			babystepsManager.disable();
		}
		phase = Phase.RED;
		originalExercise = validExercise = exercise;
		
		bus.post(new ExerciseEvent(validExercise));
		trackingManager.reset();
		babystepsManager.start(originalExercise.getBabyStepsTestTime());
	}
	
	public void displayTracking() {
		trackingManager.displayInNewWindow();
	}
}
