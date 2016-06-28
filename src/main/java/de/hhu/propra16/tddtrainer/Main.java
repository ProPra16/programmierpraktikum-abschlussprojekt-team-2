package de.hhu.propra16.tddtrainer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("TDDTrainer");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			primaryStage.setScene(new Scene(rootLayout));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		primaryStage.show();
		showEditorView();
	}

	private void showEditorView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/EditorView.fxml"));
			rootLayout.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
