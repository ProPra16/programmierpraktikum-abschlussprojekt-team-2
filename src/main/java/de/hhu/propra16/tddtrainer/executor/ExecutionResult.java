package de.hhu.propra16.tddtrainer.executor;

/*
 * Saves the result of the execution of the program and tests.
 */

import vk.core.api.*;

public class ExecutionResult {

	private CompilerResult compilerResult;
	private TestResult testResult;
	
	public ExecutionResult(CompilerResult cr) {
		compilerResult = cr;
	}
	
	public ExecutionResult(CompilerResult cr, TestResult tr) {
		compilerResult = cr;
		testResult = tr;
	}
	
	public CompilerResult getCompilerResult() {
		return compilerResult;
	}
	
	public TestResult getTestResult() {
		return testResult;
	}
	
}
