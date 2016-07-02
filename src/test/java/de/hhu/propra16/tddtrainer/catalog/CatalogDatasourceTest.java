package de.hhu.propra16.tddtrainer.catalog;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class CatalogDatasourceTest {

	@Test
	public void testFakeCatalogDatasource() {
		CatalogDatasourceIF catalogDs = new FakeCatalogDatasource();
		List<Exercise> excercises = catalogDs.loadCatalog();
		String code1 = excercises.get(0).getCode(0).getCode();
		String test1 = excercises.get(0).getTest(0).getCode();

		assertEquals("public class WorkingCode {public int returnOne() {return 1;}}", code1);
		assertEquals("import static org.junit.Assert.*; import org.junit.Test; public class WorkingTest {@Test public void testCode() {WorkingCode c = new WorkingCode(); assertEquals(1, c.returnOne());}}", test1);
		
		String code2 = excercises.get(1).getCode(0).getCode();
		String test2 = excercises.get(1).getTest(0).getCode();

		assertEquals("public class CompileErrorCode {public int returnOne() {return 1;}}", code2);
		assertEquals("import static org.junit.Assert.*; import org.junit.Test; public class CompileErrorTest {@Test public void testCode() {CompileErrorCode c = new CompileErrorCode(); assertEquals(2, c.returnTwo());}}", test2);

		
		String code3 = excercises.get(2).getCode(0).getCode();
		String test3 = excercises.get(2).getTest(0).getCode();

		assertEquals("public class TestErrorCode {public int returnOne() {return 2;}}", code3);
		assertEquals("import static org.junit.Assert.*; import org.junit.Test; public class TestErrorTest {@Test public void testCode() {TestErrorCode c = new TestErrorCode(); assertEquals(1, c.returnOne());}}", test3);

		
		
		
	}
	
	/*
	 * No assertEquals since the test depends on the catalog file
	 * but there shouldn't occur an exception while reading and parsing the xml
	 */
	@Test
	public void testXMLCatalogDatasource() {
		CatalogDatasourceIF catalogDs = new XMLCatalogDatasource(new File("catalog.xml"));
		List<Exercise> excercises = catalogDs.loadCatalog();
		
		for(Exercise exercise : excercises){
			System.out.println(exercise);
		}
	}

}
