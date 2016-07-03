package de.hhu.propra16.tddtrainer.gui.catalog;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class ExerciseSelectorController {
	
	@FXML private ListView<Exercise> exerciseList;
	@FXML private TextArea descriptionField;
	@FXML private Button selectButton;
	@FXML private Button cancelButton;
	
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
		
		FakeCatalogDatasource ds = new FakeCatalogDatasource();
		exerciseList.setItems(FXCollections.observableArrayList(ds.loadCatalog()));
		
		//TODO description bei Auswahl anzeigen
		
	}

}
