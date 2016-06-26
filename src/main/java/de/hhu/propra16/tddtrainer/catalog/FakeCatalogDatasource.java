package de.hhu.propra16.tddtrainer.catalog;

import java.util.ArrayList;
import java.util.List;

public class FakeCatalogDatasource implements CatalogDatasourceIF {

	@Override
	public List<Exercise> loadCatalog() {
		ArrayList<Exercise> catalog = new ArrayList<>();
		Exercise ex = new Exercise("Excercise 1", "Test");
		JavaClass code = new JavaClass("WorkingCode", "public class WorkingCode {public int returnOne() {return 1;}}");
		JavaClass test = new JavaClass("WorkingTest", "import static org.junit.Assert.*; import org.junit.Test; public class WorkingTest {	@Test public void testCode() {WorkingCode c = new WorkingCode(); assertEquals(1, c.returnOne());}}");
		ex.addCode(code);
		ex.addTest(test);
		
		catalog.add(ex);
		
		return catalog;
	}

}
