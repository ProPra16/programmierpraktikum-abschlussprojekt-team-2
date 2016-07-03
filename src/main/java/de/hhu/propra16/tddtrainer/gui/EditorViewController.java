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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class EditorViewController {

	@FXML
	private TextArea tests;

	@FXML
	private TextArea code;

	@FXML
	private Label statusLabel;

	public void initialize() {
		MyEventBus.getInstance().register(this);
	}

	@FXML
	private void handleNextStep(ActionEvent event) {

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

	private void changePhaseToRefactor() {
		statusLabel.setText("REFACTOR");
	}

	private void changePhaseToGreen() {
		statusLabel.setText("GREEN");
	}

	private void changePhaseToRed() {
		statusLabel.setText("RED");
	}

}
