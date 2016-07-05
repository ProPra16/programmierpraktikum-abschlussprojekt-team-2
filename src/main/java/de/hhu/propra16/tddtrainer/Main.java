package de.hhu.propra16.tddtrainer;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.tools.ToolProvider;

import com.google.common.eventbus.EventBus;

import de.hhu.propra16.tddtrainer.catalog.CatalogDatasourceIF;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import de.hhu.propra16.tddtrainer.gui.RootLayoutController;
import de.hhu.propra16.tddtrainer.gui.catalog.ExerciseSelector;
import de.hhu.propra16.tddtrainer.logic.PhaseManager;
import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;
import de.hhu.propra16.tddtrainer.tracking.TrackingManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private BorderPane rootLayout;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if(ToolProvider.getSystemJavaCompiler() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: No Java Compiler");
			alert.setHeaderText(null);
			alert.setContentText("This application requires a java compiler to function.\n\n"
					+ "Please run the application with a JDK of version 1.8.0_40 or higher.");
			
			alert.showAndWait();
			return;
		}
		
		EventBus bus = new EventBus();
		TrackingManager trackingManager = new TrackingManager();
		CatalogDatasourceIF datasource = new FakeCatalogDatasource();
		ExerciseSelector exerciseSelector = new ExerciseSelector(datasource);
		
		PhaseManagerIF phaseManager = new PhaseManager(trackingManager, exerciseSelector, bus);
		
		primaryStage.setTitle("TDDTrainer");

		FXMLLoader loader = new FXMLLoader();
		Locale locale = new Locale("en", "EN");
		loader.setResources(ResourceBundle.getBundle("bundles.tddt", locale));
		loader.setLocation(Main.class.getResource("gui/RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();
		RootLayoutController controller = loader.getController();
		controller.init(phaseManager, bus);
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.setOnCloseRequest((e) -> System.exit(0));
		primaryStage.show();
		primaryStage.setMinWidth(1000);
		primaryStage.setMinHeight(600);
	}

}