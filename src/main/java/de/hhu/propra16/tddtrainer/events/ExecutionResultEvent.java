package de.hhu.propra16.tddtrainer.events;

import java.util.ArrayList;
import java.util.List;

import de.hhu.propra16.tddtrainer.executor.CompilationResult;
import de.hhu.propra16.tddtrainer.executor.ExecutionResult;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;

public class ExecutionResultEvent {

	private ExecutionResult executionResult;
	
	public ExecutionResultEvent(ExecutionResult executionResult) {
		this.executionResult = executionResult;
	}
	
	public String getExecutionResultAsString() {
		String executionResultAsString = "Compile Errors: ";
		if(executionResult.getCompilerResult().hasCompileErrors()) {
			List<CompilationResult> compilationResultsWithErrors = new ArrayList<>();
			for(CompilationResult cr : executionResult.getCompileErrors()) {
				if(!cr.getCompileErrors().isEmpty()) {
					compilationResultsWithErrors.add(cr);
				}
			}
			executionResultAsString += compilationResultsWithErrors.size() + "\n";
			for(CompilationResult cr : compilationResultsWithErrors) {
				executionResultAsString += "Class: " + cr.getClassName() + ", Errors: " + cr.getCompileErrors().size() + "\n";
				for(CompileError ce : cr.getCompileErrors()) {
					executionResultAsString += "Line " + ce.getLineNumber() + ": " + ce.getCodeLineContainingTheError().trim() + "\n" + ce.getMessage() + "\n"; 
				}
			}
		}
		else {
			executionResultAsString += "0\nSuccessful Tests: ";
			if(executionResult.getTestResult().getNumberOfFailedTests() != 0) {
				executionResultAsString += executionResult.getTestResult().getNumberOfSuccessfulTests() + ", Failed Tests: " + executionResult.getTestResult().getNumberOfFailedTests() + "\n";
				for(TestFailure tf : executionResult.getTestResult().getTestFailures()) {
					executionResultAsString += "Class: " + tf.getTestClassName() + ", Method: " + tf.getMethodName() + "\n" + tf.getMessage() +"\n";
				}
			}
			else {
				executionResultAsString += executionResult.getTestResult().getNumberOfSuccessfulTests() + ", Failed Tests: 0\n";
			}
		}
		return executionResultAsString;
	}
	
}
