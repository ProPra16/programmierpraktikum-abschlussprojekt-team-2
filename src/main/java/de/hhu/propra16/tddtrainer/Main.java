package de.hhu.propra16.tddtrainer;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private BorderPane rootLayout;
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("TDDTrainer");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			Locale locale = new Locale("en", "EN");
			loader.setResources(ResourceBundle.getBundle("bundles.tddt", locale));
			loader.setLocation(Main.class.getResource("gui/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			primaryStage.setScene(new Scene(rootLayout));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		primaryStage.show();
	}
	
}