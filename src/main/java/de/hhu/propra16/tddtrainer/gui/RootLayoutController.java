package de.hhu.propra16.tddtrainer.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import de.hhu.propra16.tddtrainer.events.MyEventBus;
import de.hhu.propra16.tddtrainer.events.NewExerciseEvent;
import de.hhu.propra16.tddtrainer.gui.catalog.ExerciseSelector;
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
	private void changeLanguage(ActionEvent event) {
		List<String> choices = new ArrayList<>();
		choices.add("English");
		choices.add("Deutsch");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
		dialog.setTitle(resources.getString("languagedialog.title"));
		dialog.setHeaderText(resources.getString("languagedialog.headerText"));
		dialog.setContentText(resources.getString("languagedialog.contentText"));

		Optional<String> result = dialog.showAndWait();

		Locale locale = result.isPresent() && result.get().equals("English") ? new Locale("en", "EN")
				: result.isPresent() && result.get().equals("Deutsch") ? new Locale("de", "DE") : resources.getLocale();

		if (!resources.getLocale().toString().equals(locale.toString().substring(0, 2))) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(resources.getString("restartdialog.title"));
			alert.setHeaderText(resources.getString("restartdialog.headerText"));
			alert.setContentText(resources.getString("restartdialog.contentText"));

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK) {
				restart(locale);
			}
		}

	}

	@FXML
	private void selectExercise(ActionEvent event) {
		// new ExerciseSelector(new FakeCatalogDatasource()).selectExercise();

		Exercise working = new Exercise("Working Excercise", "This is a working exercise");
		working.addCode(new JavaClass("WorkingCode",
				"public class WorkingCode {\n    public int returnOne() {\n        return 1;\n    }\n}"));
		working.addTest(new JavaClass("WorkingTest",
				"import static org.junit.Assert.*;\nimport org.junit.Test;\n\npublic class WorkingTest {\n\n    @Test\n    public void testCode() {\n        WorkingCode c = new WorkingCode();\n        assertEquals(1, c.returnOne());\n    }\n}"));

		MyEventBus.getInstance().post(new NewExerciseEvent(working));
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
