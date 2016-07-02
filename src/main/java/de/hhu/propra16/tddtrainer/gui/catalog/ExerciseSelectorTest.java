package de.hhu.propra16.tddtrainer.gui.catalog;

import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Temporary Class
 */
public class ExerciseSelectorTest extends Application {

	public static void main(String[] as){
		launch(as);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new ExerciseSelector(new FakeCatalogDatasource()).selectExercise();
	}
	
}
