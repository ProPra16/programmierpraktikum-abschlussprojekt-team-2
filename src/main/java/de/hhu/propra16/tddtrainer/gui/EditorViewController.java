package de.hhu.propra16.tddtrainer.gui;

import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import de.hhu.propra16.tddtrainer.events.ExecutionResultEvent;
import de.hhu.propra16.tddtrainer.events.ExerciseEvent;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EditorViewController {

	private JavaCodeArea tests;
	private JavaCodeArea code;

	@FXML
	private TextArea console;

	@FXML
	private AnchorPane codePane;

	@FXML
	private AnchorPane testPane;

	@FXML
	private Label codeLabel;

	@FXML
	private Label testLabel;

	@FXML
	private HBox iGreenBox;

	@FXML
	private HBox codeBox;

	private PhaseManagerIF phaseManager;
	private RootLayoutController rootLayoutController;
	private boolean guidisabled;
	private boolean tutorialMode = true;

	public void initialize() {
		iGreenBox.setVisible(false);
		addEditors();
	}

	protected void init(PhaseManagerIF phaseManager, RootLayoutController rootLayoutController) {
		this.phaseManager = phaseManager;
		this.rootLayoutController = rootLayoutController;
		rootLayoutController.enableReset(false);
		rootLayoutController.enableShowDescription(false);
		rootLayoutController.iRedBox.setVisible(false);
	}

	@Subscribe
	public void showExercise(ExerciseEvent exerciseEvent) {
		Exercise exercise = exerciseEvent.getExercise();

		if (exercise != null) {
			guidisabled = false;
			showExercise(exercise);
			changePhase(phaseManager.checkPhase(exercise, false));
			rootLayoutController.exerciseLabel.setText(exercise.getName());
			rootLayoutController.exerciseLabel.setTooltip(new Tooltip(exercise.getName()));
			rootLayoutController.enableShowDescription(true);
		}
		rootLayoutController.nextStepButton.setDisable(guidisabled);
	}

	public void showExercise(Exercise exercise) {
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
	}

	void changePhase(PhaseStatus phaseStatus) {
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
		rootLayoutController.statusLabel.setText("red");
		rootLayoutController.statusLabel.getStyleClass().clear();
		rootLayoutController.statusLabel.getStyleClass().add("statuslabel-red");
		code.disable(true);
		tests.disable(false);
		rootLayoutController.enableReset(true);
		tests.setStyle("-fx-border-color: crimson;");
		code.setStyle("-fx-border-color: transparent;");
		if (tutorialMode) {
			rootLayoutController.iRedBox.setVisible(true);
			rootLayoutController.iRedBox.toFront();
			iGreenBox.setVisible(false);
		}
		AnchorPane.setRightAnchor(codeBox, 15.0);
	}

	private void changePhaseToGreen() {
		rootLayoutController.statusLabel.setText("green");
		rootLayoutController.statusLabel.getStyleClass().clear();
		rootLayoutController.statusLabel.getStyleClass().add("statuslabel-green");
		code.disable(false);
		tests.disable(true);
		rootLayoutController.enableReset(true);
		code.setStyle("-fx-border-color: forestgreen;");
		tests.setStyle("-fx-border-color: transparent;");
		if (tutorialMode) {
			rootLayoutController.iRedBox.setVisible(false);
			iGreenBox.setVisible(true);
			AnchorPane.setRightAnchor(codeBox, iGreenBox.getWidth() + 10);
		}
	}

	private void changePhaseToRefactor() {
		rootLayoutController.statusLabel.setText("refactor");
		rootLayoutController.statusLabel.getStyleClass().clear();
		rootLayoutController.statusLabel.getStyleClass().add("statuslabel-refactor");
		rootLayoutController.timeLabel.setText("");
		rootLayoutController.timerImage.setVisible(false);
		code.disable(false);
		tests.disable(false);
		rootLayoutController.enableReset(false);
		tests.setStyle("-fx-border-color: grey;");
		code.setStyle("-fx-border-color: grey;");
		if (tutorialMode) {
			rootLayoutController.iRedBox.setVisible(false);
			iGreenBox.setVisible(false);
		}
		AnchorPane.setRightAnchor(codeBox, 15.0);
	}

	Exercise newExerciseFromCurrentInput() {
		Exercise oldExercise = phaseManager.getOriginalExercise();
		Exercise exercise = new Exercise(oldExercise.getName(), oldExercise.getDescription());
		exercise.addCode(new JavaClass(oldExercise.getCode(0).getName(), code.getText()));
		exercise.addTest(new JavaClass(oldExercise.getTest(0).getName(), tests.getText()));
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

	@Subscribe
	public void showExecutionResult(ExecutionResultEvent event) {
		PhaseStatus status = event.getPhaseStatus();
		console.setText(status.getExecutionResultAsString());

		if (status.isValid()) {
			console.setStyle("-fx-text-fill: grey");
		} else {
			console.setStyle("-fx-text-fill: red");
		}
	}

	protected void setTutorialMode(boolean selected) {
		tutorialMode = selected;
		if (!selected) {
			rootLayoutController.iRedBox.setVisible(false);
			iGreenBox.setVisible(false);
			AnchorPane.setRightAnchor(codeBox, 15.0);
		}
		phaseManager.resetPhase();
	}

}
