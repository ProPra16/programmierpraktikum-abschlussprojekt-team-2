package de.hhu.propra16.tddtrainer.gui.catalog;

import java.io.IOException;
import java.util.Optional;

import de.hhu.propra16.tddtrainer.catalog.CatalogDatasourceIF;
import de.hhu.propra16.tddtrainer.catalog.Exercise;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

public class ExerciseSelector {
	
	private CatalogDatasourceIF dataSource;

	public ExerciseSelector(CatalogDatasourceIF dataSource){
		this.dataSource = dataSource;
	}
	
	public Exercise selectExercise(){
		
		Dialog<Exercise> dialog = new Dialog<>();

		try {
			BorderPane pane = (BorderPane) FXMLLoader.load(this.getClass().getResource("ExerciseSelector.fxml"));
			dialog.getDialogPane().getChildren().add(pane);
			dialog.setResizable(true);

		
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		dialog.setResult(null); //TODO ausgewählte Excercise
		
		Optional<Exercise> exerciseOptional = dialog.showAndWait();
		if(exerciseOptional.isPresent()){
			return exerciseOptional.get();
		} else {
			return null;
		}
	}

	
}
