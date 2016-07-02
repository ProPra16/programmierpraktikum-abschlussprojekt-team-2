package de.hhu.propra16.tddtrainer.tracking;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import de.hhu.propra16.tddtrainer.executor.ExecutionResult;
import de.hhu.propra16.tddtrainer.executor.Executor;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		TrackingManager trackingManager = new TrackingManager();
		FakeCatalogDatasource fake = new FakeCatalogDatasource();
		Exercise exercise = fake.loadCatalog().get(0);
		
		Executor executor = new Executor();
		ExecutionResult result = executor.execute(exercise);
		
		PhaseStatus phaseStatus = new PhaseStatus(true, result, Phase.RED);
		PhaseStatus phaseStatus2 = new PhaseStatus(true, result, Phase.GREEN);
		PhaseStatus phaseStatus3 = new PhaseStatus(false, result, Phase.REFACTOR);
		
		Thread.sleep(1000);
		
		trackingManager.track(exercise, phaseStatus);
		
		Thread.sleep(2000);
		
		trackingManager.track(exercise, phaseStatus2);
		
		Thread.sleep(3000);
		
		trackingManager.track(exercise, phaseStatus3);
		
		trackingManager.displayInNewWindow();
		
	}

}
