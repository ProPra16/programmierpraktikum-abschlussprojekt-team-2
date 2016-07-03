package de.hhu.propra16.tddtrainer.tracking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Trackline {
	
    static ListView<String> listViewCompile = new ListView<>();
    static ListView<String> listViewTest = new ListView<>();
	
    public static void popup(TrackingManager trackingManager) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tracked Progress");
        stage.setMinWidth(500);
        stage.setMinHeight(250);

        Label header = new Label();
        
        try {
			header.setText("Progress of exercise " + trackingManager.progress.get(0).exercise.getName());
		} catch (NullPointerException e) {
			header.setText("Get started to view your progress!");
		} finally {
			header.setFont(new Font("Arial", 20));
		}
        Button closeButton = new Button("Close");
        
        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox(10);
        HBox hbox = generateTrackline(trackingManager); 
        
        listViewCompile.getItems().add("CompilationResult");
        listViewTest.getItems().add("TestResult");
        
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(listViewCompile, listViewTest);
        hbox2.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(header, hbox, hbox2, closeButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.showAndWait();

    }
    
    	private static HBox generateTrackline(TrackingManager trackingManager) {
    		HBox hbox = new HBox();
    		
    		int size = trackingManager.progress.size();
    		
    		long totalWorktime = getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(size-1).pointOfTime); 
    		long timeOfSnap;
    		
    		for (int i = 0; i<size; i++) {
    			timeOfSnap = 0;
    			if(i == 0) {
    				timeOfSnap =  getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(0).pointOfTime);
    			}
    			
    			else { 
    				timeOfSnap = getTimeBetweenSnaps(trackingManager.progress.get(i-1).pointOfTime, trackingManager.progress.get(i).pointOfTime);
    			}
    			
				MyRectangle rectangle = new MyRectangle((timeOfSnap/(double) totalWorktime)*500.0, 50.0, trackingManager.progress.get(i));
				rectangle.setStroke(Color.BLACK);
				hbox.getChildren().add(rectangle);
				
			}
    		return hbox;
    	}
    	
    		
    	private static long getTimeBetweenSnaps(LocalDateTime start, LocalDateTime end) {
    		
    		return start.until(end, ChronoUnit.SECONDS);
    		
    	}

}









