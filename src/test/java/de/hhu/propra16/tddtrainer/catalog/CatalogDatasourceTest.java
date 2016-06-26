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
		Exercise ex = excercises.get(0);
		String code = ex.getCode(0).getCode();
		String test = ex.getTest(0).getCode();

		assertEquals("public class WorkingCode {public int returnOne() {return 1;}}", code);
		assertEquals("import static org.junit.Assert.*; import org.junit.Test; public class WorkingTest {	@Test public void testCode() {WorkingCode c = new WorkingCode(); assertEquals(1, c.returnOne());}}", test);
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
