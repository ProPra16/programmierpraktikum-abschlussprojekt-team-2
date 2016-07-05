package de.hhu.propra16.tddtrainer.gui.catalog;

import de.hhu.propra16.tddtrainer.catalog.CatalogDatasourceIF;
import de.hhu.propra16.tddtrainer.catalog.Exercise;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ExerciseSelectorController {
	
	@FXML private ListView<Exercise> exerciseList;
	@FXML private TextArea descriptionField;
	@FXML private Button selectButton;
	@FXML private Button cancelButton;
	
	private CatalogDatasourceIF datasource;
	private Stage stage;
	private Exercise selectedExercise;
	
	@FXML
	public void initialize(){

		exerciseList.setCellFactory(new Callback<ListView<Exercise>, ListCell<Exercise>>() {
			
			@Override
			public ListCell<Exercise> call(ListView<Exercise> param) {
				
				ListCell<Exercise> cell = new ListCell<Exercise>(){
					@Override
					protected void updateItem(Exercise item, boolean empty) {
						super.updateItem(item, empty);
						
						 if (empty || item == null) {
					         setText(null);
					         setGraphic(null);
					     } else {
					         setText(item.getName());
					     }
					}
				};
				
				return cell;
			}
		});
		
		exerciseList.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
			selectButton.setDisable(false);
			descriptionField.setText(newValue.getDescription());
		});
		
		EventHandler<KeyEvent> eventHandler = (e) -> {
			if(e.getCode() == KeyCode.ENTER){
				exerciseList.getParent().fireEvent(e);
			}
		};
		
		exerciseList.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
		descriptionField.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
	}
	
	public void selectButtonAction(){
		Exercise selectedExercise = exerciseList.getSelectionModel().getSelectedItem();
		if(selectedExercise != null){
			this.selectedExercise = selectedExercise;
			stage.close();
		}
	}
	
	public void cancelButtonAction(){
		selectedExercise = null;
		stage.close();
	}
	
	public void listViewAction(){
		Exercise selectedExercise = exerciseList.getSelectionModel().getSelectedItem();
		System.out.println(selectedExercise);
	}

	public void setDatasource(CatalogDatasourceIF datasource){
		this.datasource = datasource;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public Exercise getSelectedExercise(){
		return selectedExercise;
	}
	
	public void loadData(){
		if(datasource == null){
			System.err.println("datasource is null");
			return;
		}
		exerciseList.setItems(FXCollections.observableArrayList(datasource.loadCatalog()));
	}

}
