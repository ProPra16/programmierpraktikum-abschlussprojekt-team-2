package de.hhu.propra16.tddtrainer.executor;

import static org.junit.Assert.*;
import org.junit.Test;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;

public class ExecutorTest {
	
	@Test
	public void testExecuteWithOneFailingTest() {
		JavaClass code = new JavaClass("Main",
			"public class Main {\n"
			+ "public static void main(String[] args) {\n"
			+ "String s = ausgabe(\"Hallo Welt\");\n"
			+ "}\n"
			+ "public static String ausgabe(String string){\n"
			+ "return string + \"!\";\n"
			+ "}\n"
			+ "}\n");
		JavaClass test = new JavaClass("JunitTest",
			"import static org.junit.Assert.*;\n"
			+ "import org.junit.Test;\n"
			+ "public class JunitTest {\n"
			+ "@Test\n"
			+ "public void testMain() {\n"
			+ "assertEquals(\"Hallo abc!\", Main.ausgabe(\"Hallo Welt\"));\n"
			+ "}\n"
			+ "}\n");
		Exercise exercise = new Exercise();
		exercise.addCode(code);
		exercise.addTest(test);
		
		ExecutionResult result = new Executor().execute(exercise);
		assertEquals(false, result.getCompilerResult().hasCompileErrors());
		assertEquals(1, result.getTestResult().getNumberOfFailedTests());
	}
	
	@Test
	public void testExecuteWithNoFailingTest() {
		JavaClass code = new JavaClass("Main",
			"public class Main {\n"
			+ "public static void main(String[] args) {\n"
			+ "String s = ausgabe(\"Hallo Welt\");\n"
			+ "}\n"
			+ "public static String ausgabe(String string){\n"
			+ "return string + \"!\";\n"
			+ "}\n"
			+ "}\n");
		JavaClass test = new JavaClass("JunitTest",
			"import static org.junit.Assert.*;\n"
			+ "import org.junit.Test;\n"
			+ "public class JunitTest {\n"
			+ "@Test\n"
			+ "public void testMain() {\n"
			+ "assertEquals(\"Hallo Welt!\", Main.ausgabe(\"Hallo Welt\"));\n"
			+ "}\n"
			+ "}\n");
		Exercise exercise = new Exercise();
		exercise.addCode(code);
		exercise.addTest(test);
		
		ExecutionResult result = new Executor().execute(exercise);
		assertEquals(false, result.getCompilerResult().hasCompileErrors());
		assertEquals(1, result.getTestResult().getNumberOfSuccessfulTests());
	}
	
	@Test
	public void testExecuteWithCompileError() {
		JavaClass code = new JavaClass("Main",
			"public class Main {\n"
			+ "public static void main(String[] args) {\n"
			+ "String s = ausgabe(\"Hallo Welt\");\n"
			+ "}\n"
			+ "public static String ausgabe(String string){\n"
			+ "return string + \"!\";\n"
			+ "}\n");
		JavaClass test = new JavaClass("JunitTest",
			"import static org.junit.Assert.*;\n"
			+ "import org.junit.Test;\n"
			+ "public class JunitTest {\n"
			+ "@Test\n"
			+ "public void testMain() {\n"
			+ "assertEquals(\"Hallo Welt!\", Main.ausgabe(\"Hallo Welt\"));\n"
			+ "}\n"
			+ "}\n");
		Exercise exercise = new Exercise();
		exercise.addCode(code);
		exercise.addTest(test);
		
		ExecutionResult result = new Executor().execute(exercise);
		assertEquals(true, result.getCompilerResult().hasCompileErrors());
	}
}
