package de.hhu.propra16.tddtrainer.gui;

import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import de.hhu.propra16.tddtrainer.events.ExerciseEvent;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EditorViewController {

	private JavaCodeArea tests;
	private JavaCodeArea code;

	@FXML
	private AnchorPane codePane;

	@FXML
	private AnchorPane testPane;

	@FXML
	private Label statusLabel;

	@FXML
	private Label exerciseLabel;

	@FXML
	private Label codeLabel;

	@FXML
	private Label testLabel;

	@FXML
	private Button nextStepButton;

	private PhaseManagerIF phaseManager;
	private RootLayoutController rootLayoutController;
	private boolean guidisabled;

	public void initialize() {
		addEditors();
	}

	@FXML
	private void handleNextStep(ActionEvent event) {
		Exercise exercise = newExerciseFromCurrentInput();
		PhaseStatus status = phaseManager.checkPhase(exercise, true);
		changePhase(status);
	}

	@Subscribe
	public void showExercise(ExerciseEvent exerciseEvent) {
		Exercise exercise = exerciseEvent.getExercise();

		if (exercise != null) {
			guidisabled = false;
			for (JavaClass jclass : exercise.getCode()) {
				boolean wasDisabled = code.isDisable();
				code.setDisable(false);
				code.clear();
				code.appendText(jclass.getCode());
				codeLabel.setText(jclass.getName());
				code.setDisable(wasDisabled);
			}

			for (JavaClass jclass : exercise.getTests()) {
				boolean wasDisabled = code.isDisable();
				tests.setDisable(false);
				tests.clear();
				tests.appendText(jclass.getCode());
				testLabel.setText(jclass.getName());
				tests.setDisable(wasDisabled);
			}
			changePhase(phaseManager.checkPhase(exercise, false));
			exerciseLabel.setText(exercise.getName());
		}
		nextStepButton.setDisable(guidisabled);
	}

	public void changePhase(PhaseStatus phaseStatus) {
		System.out.println(phaseStatus.isValid());
		Phase phase = phaseStatus.getPhase();

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
		code.disable(true);
		tests.disable(false);
		rootLayoutController.enableReset(true);
	}

	private void changePhaseToGreen() {
		statusLabel.setText("green");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-green");
		code.disable(false);
		tests.disable(true);
		rootLayoutController.enableReset(true);
	}

	private void changePhaseToRefactor() {
		statusLabel.setText("refactor");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-refactor");
		code.disable(false);
		tests.disable(false);
		rootLayoutController.enableReset(false);
	}

	private Exercise newExerciseFromCurrentInput() {
		Exercise exercise = new Exercise(exerciseLabel.getText(), "");
		exercise.addCode(new JavaClass(codeLabel.getText(), code.getText()));
		exercise.addTest(new JavaClass(testLabel.getText(), tests.getText()));
		return exercise;
	}

	private void addEditors() {
		code = new JavaCodeArea();
		code.disable(true);
		codePane.getChildren().add(code);
		AnchorPane.setTopAnchor(code, 50.0);
		AnchorPane.setLeftAnchor(code, 20.0);
		AnchorPane.setRightAnchor(code, 20.0);
		AnchorPane.setBottomAnchor(code, 5.0);

		tests = new JavaCodeArea();
		tests.disable(true);
		testPane.getChildren().add(tests);
		AnchorPane.setTopAnchor(tests, 50.0);
		AnchorPane.setLeftAnchor(tests, 20.0);
		AnchorPane.setRightAnchor(tests, 20.0);
		AnchorPane.setBottomAnchor(tests, 5.0);
	}

	public void init(PhaseManagerIF phaseManager, RootLayoutController rootLayoutController) {
		this.phaseManager = phaseManager;
		this.rootLayoutController = rootLayoutController;
		rootLayoutController.enableReset(false);
	}

}
