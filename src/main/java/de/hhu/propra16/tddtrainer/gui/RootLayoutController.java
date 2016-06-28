package de.hhu.propra16.tddtrainer.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import de.hhu.propra16.tddtrainer.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootLayoutController implements Initializable {


    @FXML
    private BorderPane root;
    
	private ResourceBundle resources;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
		showEditorView();
	}

	private void showEditorView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("EditorView.fxml"));
			loader.setResources(resources);
			root.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void changeLanguage(ActionEvent event) {
		List<String> choices = new ArrayList<>();
		choices.add("English");
		choices.add("Deutsch");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
		dialog.setTitle(resources.getString("languagedialog.title"));
		dialog.setHeaderText(resources.getString("languagedialog.headerText"));
		dialog.setContentText(resources.getString("languagedialog.contentText"));
		
		Optional<String> result = dialog.showAndWait();
		
		Locale locale = result.isPresent() && result.get().equals("English")? new Locale("en", "EN") :
			result.isPresent() && result.get().equals("Deutsch")? new Locale("de", "DE") : 
				resources.getLocale();
			
		if(!resources.getLocale().toString().equals(locale.toString().substring(0, 2))) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(resources.getString("restartdialog.title"));
			alert.setHeaderText(resources.getString("restartdialog.headerText"));
			alert.setContentText(resources.getString("restartdialog.contentText"));

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK){
			    restart(locale);
			} 
		}

	}

	private void restart(Locale locale) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("bundles.tddt", locale));
            loader.setLocation(this.getClass().getResource("RootLayout.fxml"));
            BorderPane rootLayout = loader.load();
            
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(rootLayout));
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
