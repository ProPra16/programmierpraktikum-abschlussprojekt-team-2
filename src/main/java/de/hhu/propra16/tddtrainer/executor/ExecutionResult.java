package de.hhu.propra16.tddtrainer.executor;

import java.util.*;

/*
 * Saves the result of the execution of the program and tests.
 * The list compileErrors contains a list of strings in which the first element is the name of the class which has failed to
 * compile and the other elements are the error messages.
 */

import vk.core.api.*;

public class ExecutionResult {

	private CompilerResult compilerResult;
	private TestResult testResult;
	private List<CompilationResult> compileErrors = new ArrayList<>();
	
	public ExecutionResult(CompilerResult cr, List<CompilationUnit> fcu) {
		compilerResult = cr;
		for(CompilationUnit cu : fcu) {
			compileErrors.add(new CompilationResult(cu.getClassName(), new ArrayList<>(cr.getCompilerErrorsForCompilationUnit(cu))));
		}
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
	
	public List<CompilationResult> getCompileErrors() {
		return compileErrors;
	}
	
}
