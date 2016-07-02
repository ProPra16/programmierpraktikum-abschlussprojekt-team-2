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
	private List<List<String>> compileErrors;
	
	public ExecutionResult(CompilerResult cr, List<CompilationUnit> fcu) {
		compilerResult = cr;
		compileErrors = new ArrayList<>();
		for(CompilationUnit cu : fcu) {
			Collection<CompileError> compilerErrors = cr.getCompilerErrorsForCompilationUnit(cu);
			List<String> errorsForEachUnit = new ArrayList<>();
			errorsForEachUnit.add(cu.getClassName());
			for(CompileError ce : compilerErrors) {
				errorsForEachUnit.add(ce.getMessage());
			}
			compileErrors.add(errorsForEachUnit);
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
	
	public List<List<String>> getCompileErrors() {
		return compileErrors;
	}
	
}
