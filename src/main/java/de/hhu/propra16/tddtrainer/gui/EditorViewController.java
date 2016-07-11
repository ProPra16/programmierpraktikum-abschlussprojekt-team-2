package de.hhu.propra16.tddtrainer.gui;

import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.JavaClass;
import de.hhu.propra16.tddtrainer.events.ExecutionResultEvent;
import de.hhu.propra16.tddtrainer.events.ExerciseEvent;
import de.hhu.propra16.tddtrainer.events.TimeEvent;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
	private Label statusLabel;

	@FXML
	private Label exerciseLabel;

	@FXML
	private Label codeLabel;

	@FXML
	private Label testLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private HBox iRedBox;

	@FXML
	private HBox iGreenBox;
	
	@FXML
	private HBox codeBox;

	@FXML
	private Button nextStepButton;

	private PhaseManagerIF phaseManager;
	private RootLayoutController rootLayoutController;
	private boolean guidisabled;
	private boolean tutorialMode;

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
			exerciseLabel.setTooltip(new Tooltip(exercise.getDescription()));
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
		tests.setStyle("-fx-border-color: crimson;");
		code.setStyle("-fx-border-color: transparent;");
		if (tutorialMode) {
			iRedBox.setVisible(true);
			iGreenBox.setVisible(false);
		}
		AnchorPane.setRightAnchor(codeBox, 15.0);
	}

	private void changePhaseToGreen() {
		statusLabel.setText("green");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-green");
		code.disable(false);
		tests.disable(true);
		rootLayoutController.enableReset(true);
		code.setStyle("-fx-border-color: forestgreen;");
		tests.setStyle("-fx-border-color: transparent;");
		if (tutorialMode) {
			iRedBox.setVisible(false);
			iGreenBox.setVisible(true);
			AnchorPane.setRightAnchor(codeBox, iGreenBox.getWidth() + 10);
		}
	}

	private void changePhaseToRefactor() {
		statusLabel.setText("refactor");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-refactor");
		code.disable(false);
		tests.disable(false);
		rootLayoutController.enableReset(false);
		tests.setStyle("-fx-border-color: grey;");
		code.setStyle("-fx-border-color: grey;");
		if (tutorialMode) {
			iRedBox.setVisible(false);
			iGreenBox.setVisible(false);
		}
		AnchorPane.setRightAnchor(codeBox, 15.0);
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
		iRedBox.setVisible(false);
		iGreenBox.setVisible(false);
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

	@Subscribe
	public void updateTime(TimeEvent event) {
		long time = event.getTime();
		Platform.runLater(() -> {
			timeLabel.setText("" + time);
			if (time <= 5) {
				timeLabel.setFont(new Font("System bold", 18.0));
			}
			if (time <= 10) {
				timeLabel.setStyle("-fx-text-fill: crimson");
			} else {
				timeLabel.setFont(new Font("System", 15.0));
				timeLabel.setStyle("-fx-text-fill: #6f8391");
			}
		});
	}

	public void setTutorialMode(boolean selected) {
		tutorialMode = selected;
		if(!selected) {
			iRedBox.setVisible(false);
			iGreenBox.setVisible(false);
			AnchorPane.setRightAnchor(codeBox, 15.0);
		}
		phaseManager.resetPhase();
	}

}
