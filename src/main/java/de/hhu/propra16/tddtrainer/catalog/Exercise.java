package de.hhu.propra16.tddtrainer.catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a java class with code and an associated test class
 * 
 * @author Marcel
 */
public class Exercise {

	private String name;
	private String description;
	private List<JavaClass> code;
	private List<JavaClass> tests;

	public Exercise() {
		code = new ArrayList<>();
		tests = new ArrayList<>();
	}

	public Exercise(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<JavaClass> getCode() {
		return code;
	}

	public List<JavaClass> getTests() {
		return tests;
	}
	
	public JavaClass getCode(int index){
		if(index >= code.size()) return null;
		return code.get(index);
	}
	
	public JavaClass getTest(int index){
		if(index >= tests.size()) return null;
		return tests.get(index);
	}
	
	public void addCode(JavaClass code){
		this.code.add(code);
	}
	
	public void addTest(JavaClass test){
		this.tests.add(test);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCode(List<JavaClass> code) {
		this.code = code;
	}

	public void setTests(List<JavaClass> test) {
		this.tests = test;
	}

}
