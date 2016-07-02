package de.hhu.propra16.tddtrainer.executor;

import java.util.List;
import vk.core.api.CompileError;

public class CompilationResult {

	private String className;
	private List<CompileError> compileErrors;

	public CompilationResult(String className, List<CompileError> compileErrors) {
		this.className = className;
		this.compileErrors = compileErrors;
	}

	public String getClassName() {
		return className;
	}

	public List<CompileError> getCompileErrors() {
		return compileErrors;
	}

}