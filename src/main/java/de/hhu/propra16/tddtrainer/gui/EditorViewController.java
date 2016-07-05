package de.hhu.propra16.tddtrainer.gui;

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
import javafx.scene.control.TextArea;

public class EditorViewController {

	@FXML
	private TextArea tests;

	@FXML
	private TextArea code;

	@FXML
	private Label statusLabel;
	
	@FXML
	private Label exerciseLabel;
	
    @FXML
    private Button nextStepButton;

	public void initialize() {
		MyEventBus.getInstance().register(this);
	}

	@FXML
	private void handleNextStep(ActionEvent event) {
		Exercise exercise = newExerciseFromCurrentInput();
		
		//TODO notify PhaseManager - IF or EventBus??
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

}
