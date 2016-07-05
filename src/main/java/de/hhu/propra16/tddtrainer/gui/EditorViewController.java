package de.hhu.propra16.tddtrainer.gui;

import org.fxmisc.richtext.CodeArea;

import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import de.hhu.propra16.tddtrainer.events.ChangePhaseEvent;
import de.hhu.propra16.tddtrainer.events.MyEventBus;
import de.hhu.propra16.tddtrainer.events.NewExerciseEvent;
import de.hhu.propra16.tddtrainer.logic.Phase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EditorViewController {

	private CodeArea tests;
	private CodeArea code;

	@FXML
	private AnchorPane codePane;
	
	@FXML
	private AnchorPane testPane;

	@FXML
	private Label statusLabel;

	@FXML
	private Label exerciseLabel;

	@FXML
	private Button nextStepButton;

	public void initialize() {
		MyEventBus.getInstance().register(this);
		
		addEditors();	
	}

	@FXML
	private void handleNextStep(ActionEvent event) {
		Exercise exercise = newExerciseFromCurrentInput();

		// TODO notify PhaseManager - IF or EventBus??
	}

	@Subscribe
	public void startNewExercise(NewExerciseEvent exerciseEvent) {
		Exercise exercise = exerciseEvent.getExercise();

		for (JavaClass jclass : exercise.getCode()) {
			code.appendText(jclass.getCode());
		}

		for (JavaClass jclass : exercise.getTests()) {
			tests.appendText(jclass.getCode());
		}
		changePhaseToRed();
		nextStepButton.setDisable(false);
		exerciseLabel.setText(exercise.getName());
	}

	@Subscribe
	public void changePhase(ChangePhaseEvent phaseEvent) {
		Phase phase = phaseEvent.getPhase();

		switch (phase) {
		case RED:
			changePhaseToRed();
			break;
		case GREEN:
			changePhaseToGreen();
			break;
		case REFACTOR:
			changePhaseToRefactor();
			break;
		}
	}

	private void changePhaseToRed() {
		statusLabel.setText("red");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-red");
		code.setDisable(true);
		tests.setDisable(false);
	}

	private void changePhaseToGreen() {
		statusLabel.setText("green");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-green");
		code.setDisable(false);
		tests.setDisable(true);
	}

	private void changePhaseToRefactor() {
		statusLabel.setText("refactor");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-refactor");
		code.setDisable(false);
		tests.setDisable(false);
	}

	private Exercise newExerciseFromCurrentInput() {
		Exercise exercise = new Exercise();
		exercise.addCode(new JavaClass("", code.getText()));
		exercise.addTest(new JavaClass("", tests.getText()));
		return exercise;
	}

	private void addEditors() {
		code = new JavaCodeArea();
		codePane.getChildren().add(code);
		AnchorPane.setTopAnchor(code, 50.0);
		AnchorPane.setLeftAnchor(code, 20.0);
		AnchorPane.setRightAnchor(code, 20.0);
		AnchorPane.setBottomAnchor(code, 5.0);
		
		tests = new JavaCodeArea();
		testPane.getChildren().add(tests);
		AnchorPane.setTopAnchor(tests, 50.0);
		AnchorPane.setLeftAnchor(tests, 20.0);
		AnchorPane.setRightAnchor(tests, 20.0);
		AnchorPane.setBottomAnchor(tests, 5.0);
	}

}
