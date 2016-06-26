package de.hhu.propra16.tddtrainer.executor;

/**
 * takes an exercise and computes the corresponding execution result.
 * returns an object of type ExecutionResult which holds an object of type CompilerResult and
 * an object of type TestResult (made by Dr. Bendisposto in his library).
 * These supply getter methods with the relevant data for the compilation and (if successful)
 * the test.
 * @author Luisa
 **/

import java.util.List;
import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import vk.core.api.*;

public class Executor {
	
	public ExecutionResult execute(Exercise exercise) {
		List<JavaClass> codeList = exercise.getCode();
		List<JavaClass> testList = exercise.getTests();
		CompilationUnit[] units = new CompilationUnit[codeList.size() + testList.size()];
		int i = 0;
		for(JavaClass code : codeList) {
			units[i++] = new CompilationUnit(code.getName(), code.getCode(), false);
		}
		for(JavaClass test : testList) {
			units[i++] = new CompilationUnit(test.getName(), test.getCode(), true);
		}
		
		JavaStringCompiler compiler = CompilerFactory.getCompiler(units);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		if(!compilerResult.hasCompileErrors()) {
			TestResult testResult = compiler.getTestResult();
			ExecutionResult executionResult = new ExecutionResult(compilerResult, testResult);
			return executionResult;
		}
		else {
			ExecutionResult executionResult = new ExecutionResult(compilerResult);
			return executionResult;
		}
		
	}

}
