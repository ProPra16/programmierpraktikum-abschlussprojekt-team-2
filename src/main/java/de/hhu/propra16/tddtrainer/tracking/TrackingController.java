package de.hhu.propra16.tddtrainer.tracking;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class TrackingController {

	private Stage stage;
	private TrackingManager trackingManager;
	
	@FXML
    private BorderPane borderPane;
    
    @FXML
    private Label labelHeader;
    
    @FXML
    private Button buttonClose;
    
    @FXML
    ListView<String> listViewCompilationResult;
    
    @FXML
    ListView<String> listViewTestResult;

    @FXML
    private HBox hboxTracking;

    
	void generateTrackline(TrackingManager trackingManager) {
		
		this.trackingManager = trackingManager;
		
		try {
			labelHeader.setText(trackingManager.bundle.getString("tracking.header2") + "'" + trackingManager.progress.get(0).exercise.getName() + "'");
			
		} catch (Exception e) {
		}
		
		generateRectangles();
		hboxTracking.setAlignment(Pos.CENTER);
		buttonClose.setOnAction(event -> stage.close());
		
	}
	
	
	private void generateRectangles() {
		
		int size = trackingManager.progress.size();
		long totalWorktime;
		
		try {
			totalWorktime = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(size-1).pointOfTime);
		} catch (ArrayIndexOutOfBoundsException e) {
			totalWorktime = 0;
		} 
		long timeOfSnap;
		
		for (int i = 0; i<size; i++) {
			timeOfSnap = 0;
			if(i == 0) {
				timeOfSnap =  TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(0).pointOfTime);
			} else { 
				timeOfSnap = TrackingManager.getTimeBetweenSnaps(trackingManager.progress.get(i-1).pointOfTime, trackingManager.progress.get(i).pointOfTime);
			}
			MyRectangle rectangle = new MyRectangle((timeOfSnap/(double) totalWorktime)*500.0, 50.0, trackingManager.progress.get(i));
			rectangle.setStroke(Color.BLACK);
			rectangle.setController(this);
			hboxTracking.getChildren().add(rectangle);
		}
	}
	
	
	void setStage(Stage stage) {
		this.stage = stage;
	}
    
}
