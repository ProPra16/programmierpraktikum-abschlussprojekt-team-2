package de.hhu.propra16.tddtrainer.catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy {@link CatalogDatasourceIF} implementation which contains three exercieses where the first has no errors, the second has a compile error and the third has a test error.
 * @author Marcel
 *
 */
public class FakeCatalogDatasource implements CatalogDatasourceIF {

	@Override
	public List<Exercise> loadCatalog() {
		ArrayList<Exercise> catalog = new ArrayList<>();
		Exercise working = new Exercise("Working Excercise", "This is a working exercise");
		working.addCode(new JavaClass("WorkingCode", "public class WorkingCode {public int returnOne() {return 1;}}"));
		working.addTest(new JavaClass("WorkingTest", "import static org.junit.Assert.*; import org.junit.Test; public class WorkingTest {@Test public void testCode() {WorkingCode c = new WorkingCode(); assertEquals(1, c.returnOne());}}"));
		catalog.add(working);

		Exercise compileError = new Exercise("CompileError Exercise", "This exercise has an compile error");
		compileError.addCode(new JavaClass("CompileErrorCode", "public class CompileErrorCode {public int returnOne() {return 1;}}"));
		compileError.addTest(new JavaClass("CompileErrorTest", "import static org.junit.Assert.*; import org.junit.Test; public class CompileErrorTest {@Test public void testCode() {CompileErrorCode c = new CompileErrorCode(); assertEquals(2, c.returnTwo());}}"));
		catalog.add(compileError);
		
		Exercise testError = new Exercise("TestError Excercise", "This exercise has an test error");
		testError.addCode(new JavaClass("TestErrorCode", "public class TestErrorCode {public int returnOne() {return 2;}}"));
		testError.addTest(new JavaClass("TestErrorTest", "import static org.junit.Assert.*; import org.junit.Test; public class TestErrorTest {@Test public void testCode() {TestErrorCode c = new TestErrorCode(); assertEquals(1, c.returnOne());}}"));
		catalog.add(testError);
		
		return catalog;
	}

}
