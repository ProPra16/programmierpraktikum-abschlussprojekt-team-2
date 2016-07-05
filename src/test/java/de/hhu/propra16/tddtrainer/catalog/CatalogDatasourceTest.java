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

		assertEquals("public class WorkingCode {\n\n\tpublic int returnOne() {\n\t\treturn 1;\n\t}\n}", code1);
		assertEquals("import static org.junit.Assert.*;\nimport org.junit.Test;\n\npublic class WorkingTest {\n\n\t@Test\n\tpublic void testCode() {\n\t\tWorkingCode c = new WorkingCode();\n\t\tassertEquals(1, c.returnOne());\n\t}\n}", test1);
		
		String code2 = excercises.get(1).getCode(0).getCode();
		String test2 = excercises.get(1).getTest(0).getCode();

		assertEquals("public class CompileErrorCode {\n\n\tpublic int returnOne() {\n\t\treturn 1;\n\t}\n}", code2);
		assertEquals("import static org.junit.Assert.*;\nimport org.junit.Test;\n\npublic class CompileErrorTest {\n\n\t@Test\n\tpublic void testCode() {\n\t\tCompileErrorCode c = new CompileErrorCode();\n\t\tassertEquals(2, c.returnTwo());\n\t}\n}", test2);

		
		String code3 = excercises.get(2).getCode(0).getCode();
		String test3 = excercises.get(2).getTest(0).getCode();

		assertEquals("public class TestErrorCode {\n\n\tpublic int returnOne() {\n\t\treturn 2;\n\t}\n}", code3);
		assertEquals("import static org.junit.Assert.*;\nimport org.junit.Test;\n\npublic class TestErrorTest {\n\n\t@Test\n\tpublic void testCode() {\n\t\tTestErrorCode c = new TestErrorCode();\n\t\tassertEquals(1, c.returnOne());\n\t}\n}", test3);

		
		
		
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
