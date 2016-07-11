package de.hhu.propra16.tddtrainer.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;

import de.hhu.propra16.tddtrainer.events.LanguageChangeEvent;
import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class RootLayoutController implements Initializable {

	@FXML
	private BorderPane root;
	
    @FXML
    private MenuItem reset;

	private ResourceBundle resources;

	private PhaseManagerIF phaseManager;

	private EventBus bus;

	private EditorViewController editorViewController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
	}

	private void showEditorView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("EditorView.fxml"));
			loader.setResources(resources);
			root.setCenter(loader.load());
			editorViewController = loader.getController();
			bus.register(editorViewController);
			editorViewController.init(phaseManager, this);
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

			this.resources = ResourceBundle.getBundle("bundles.tddt", locale);
			bus.post(new LanguageChangeEvent(resources));
			phaseManager.resetPhase();
		}

	}

	@FXML
	private void selectExercise(ActionEvent event) {
		phaseManager.selectExercise();
	}
	
	@FXML
	private void showProgress(ActionEvent event) {
		phaseManager.displayTracking();
	}
	
	@FXML
	private void reset(ActionEvent event) {
		phaseManager.resetPhase();
	}

	@FXML
	private void handleTutorialMode(ActionEvent event) {
		CheckMenuItem item = (CheckMenuItem) event.getSource();
		editorViewController.setTutorialMode(item.isSelected());
	}

	public void init(PhaseManagerIF phaseManager, EventBus bus) {
		this.phaseManager = phaseManager;
		this.bus = bus;
		showEditorView();
	}

	protected void enableReset(boolean enable) {
		reset.setDisable(!enable);
	}

}
