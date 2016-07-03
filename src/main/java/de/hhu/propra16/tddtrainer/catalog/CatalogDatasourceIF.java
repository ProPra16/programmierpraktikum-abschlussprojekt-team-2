package de.hhu.propra16.tddtrainer.catalog;

import java.util.List;

/**
 * Interface for a catalog datasource
 * @author Marcel
 */
public interface CatalogDatasourceIF {

	/**
	 * Loads the {@link Exercise Exercises} from the data source and returns them as a {@link List}
	 * @return a {@link List} with all {@link Exercise Exercises} from the data source
	 */
	public List<Exercise> loadCatalog();
	
}
