package de.hhu.propra16.tddtrainer.gui;

import org.fxmisc.richtext.CodeArea;

import com.google.common.eventbus.EventBus;
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
		changePhaseToRed();
		nextStepButton.setDisable(false);
		exerciseLabel.setText(exercise.getName());
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
		code.setStylesheet(true);
		code.setDisable(true);
		tests.setDisable(false);
		tests.setStylesheet(false);
	}

	private void changePhaseToGreen() {
		statusLabel.setText("green");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-green");		
		code.setDisable(false);
		code.setStylesheet(false);
		tests.setStylesheet(true);
		tests.setDisable(true);	
	}

	private void changePhaseToRefactor() {
		statusLabel.setText("refactor");
		statusLabel.getStyleClass().clear();
		statusLabel.getStyleClass().add("statuslabel-refactor");
		code.setDisable(false);
		code.setStylesheet(false);
		tests.setDisable(false);
		tests.setStylesheet(false);
	}

	private Exercise newExerciseFromCurrentInput() {
		Exercise exercise = new Exercise();
		exercise.addCode(new JavaClass(codeLabel.getText(), code.getText()));
		exercise.addTest(new JavaClass(testLabel.getText(), tests.getText()));
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

	public void init(PhaseManagerIF phaseManager, EventBus bus) {
		this.phaseManager = phaseManager;
		bus.register(this);
	}

}
