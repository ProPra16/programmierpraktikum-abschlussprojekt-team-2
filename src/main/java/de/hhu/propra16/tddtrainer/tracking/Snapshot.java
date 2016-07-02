package de.hhu.propra16.tddtrainer.tracking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.executor.CompilationResult;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.scene.paint.Color;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class Snapshot {

	Exercise exercise;
	LocalDateTime pointOfTime;
	PhaseStatus phaseStatus;

	public Snapshot(Exercise exercise, LocalDateTime pointOfTime, PhaseStatus phaseStatus) {
		this.exercise = exercise; 
		this.pointOfTime = pointOfTime;
		this.phaseStatus = phaseStatus;
	}
	
	Color getColor(){
		
	    	switch(phaseStatus.getPhase()) {
	    	case RED:
	    		return Color.RED;
	    	case GREEN:
	    		return Color.GREEN;
	    	default:
	    		return Color.BLUE;
	    	}
	}
	
	List<String> compilationResultToList() {
		
		List<String> result = new ArrayList<>();

		for(CompilationResult compilationResult:  phaseStatus.getExecutionResult().getCompileErrors()) {
			List<CompileError> compileErrors = compilationResult.getCompileErrors();
			for(CompileError compileError : compileErrors) {
				String outputCompile = compileError.getMessage();
				result.add(outputCompile);
			}
		}

		
		
		
				
		return result;
	}
	
	List<String> testResultToList(){
		
		List<String> result = new ArrayList<>();
		
		TestResult testresult = phaseStatus.getExecutionResult().getTestResult();
		Collection<TestFailure> testFailures = testresult.getTestFailures();
		
		for(TestFailure testFail : testFailures) {
			String outputTest = testFail.getMessage();
			result.add(outputTest);
		}
		
		return result;
		 
	}
	
}
