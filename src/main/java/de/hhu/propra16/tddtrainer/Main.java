package de.hhu.propra16.tddtrainer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.tools.ToolProvider;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.catalog.CatalogDatasourceIF;
import de.hhu.propra16.tddtrainer.catalog.XMLCatalogDatasource;
import de.hhu.propra16.tddtrainer.events.LanguageChangeEvent;
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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Main Class to get the Application started.
 *
 */
public class Main extends Application {
	private BorderPane rootLayout;
	private Stage primaryStage;
	private EventBus bus;
	private PhaseManagerIF phaseManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("TDDTrainer");
		primaryStage.getIcons().add(new Image("/de/hhu/propra16/tddtrainer/gui/app_icon.png"));
		primaryStage.setOnCloseRequest((e) -> System.exit(0));
		if(ToolProvider.getSystemJavaCompiler() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: No Java Compiler");
			alert.setHeaderText(null);
			alert.setContentText("This application requires a java compiler to function.\n\n"
					+ "Please run the application with a JDK of version 1.8.0_40 or higher.");
			
			alert.showAndWait();
			return;
		}
		
		bus = new EventBus();
		bus.register(this);
		TrackingManager trackingManager = new TrackingManager();
		bus.register(trackingManager);
		CatalogDatasourceIF datasource = new XMLCatalogDatasource(new File("catalog.xml"));
		ExerciseSelector exerciseSelector = new ExerciseSelector(datasource);
		bus.register(exerciseSelector);
		phaseManager = new PhaseManager(trackingManager, exerciseSelector, bus);
		
		Locale locale = new Locale("en", "EN");
		ResourceBundle bundle = ResourceBundle.getBundle("bundles.tddt", locale);
		bus.post(new LanguageChangeEvent(bundle));
	}
	
	@Subscribe
	public void initRootLayout(LanguageChangeEvent event) throws IOException {
		ResourceBundle bundle = event.getBundle();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(bundle);
		loader.setLocation(Main.class.getResource("gui/RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();
		RootLayoutController controller = loader.getController();
		controller.init(phaseManager, bus);
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.show();
		primaryStage.setMinWidth(1000);
		primaryStage.setMinHeight(600);
	}
}