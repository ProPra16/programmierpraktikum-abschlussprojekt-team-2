package de.hhu.propra16.tddtrainer.tracking;

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
	
//    static ListView<String> listViewCompile = new ListView<>();
//    static ListView<String> listViewTest = new ListView<>();
	
//    public static void popup(TrackingManager trackingManager) {
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Tracked Progress");
//        stage.setMinWidth(500);
//        stage.setMinHeight(350);
//
//        Label header = new Label();
//        
//        try {
//			header.setText("Progress of: " + trackingManager.progress.get(0).exercise.getName());
//		} 
//    	  catch (IndexOutOfBoundsException e) {
//			header.setText("Get started to view your progress!");
    
//		} 
//    	  finally {
//			header.setFont(new Font("Arial", 20));
//		}
//        Button closeButton = new Button("Close");
//        closeButton.setOnAction(event -> stage.close());

//        VBox vbox = new VBox(10);
//        HBox hboxTrackline = generateTrackline(trackingManager); 
        
//        Label compilationResultLabel = new Label("CompilationResult");
//        Label testResultLabel	= new Label("TestResult");
        
//        HBox hboxListView = new HBox();
        
        
//        VBox vboxCompile = new VBox();
//        vboxCompile.getChildren().addAll(compilationResultLabel, listViewCompile);
//        vboxCompile.setAlignment(Pos.CENTER);
        
        
//        VBox vboxTest = new VBox();
//        vboxTest.getChildren().addAll(testResultLabel, listViewTest);
//        vboxTest.setAlignment(Pos.CENTER);
        
        
//        hboxListView.getChildren().addAll(vboxCompile, vboxTest);
//        hboxListView.setAlignment(Pos.CENTER);
        
//        hboxTrackline.setAlignment(Pos.CENTER);
        
//        vbox.getChildren().addAll(header, hboxTrackline, hboxListView, closeButton);
//        vbox.setAlignment(Pos.CENTER);

//        Scene scene = new Scene(vbox);
//        stage.setScene(scene);
//        stage.showAndWait();
//    }
    
//    	private static HBox generateTrackline(TrackingManager trackingManager) {
//    		HBox hbox = new HBox();
//    		int size = trackingManager.progress.size();
//    		
//    		long totalWorktime;
//			try {
//				totalWorktime = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(size-1).pointOfTime);
//			} catch (ArrayIndexOutOfBoundsException e) {
//				totalWorktime = 0;
//			} 
//    		long timeOfSnap;
//    		
//    		for (int i = 0; i<size; i++) {
//    			System.out.println(i);
//    			timeOfSnap = 0;
//    			if(i == 0) {
//    				timeOfSnap =  TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(0).pointOfTime);
//    			} else { 
//    				timeOfSnap = TrackingManager.getTimeBetweenSnaps(trackingManager.progress.get(i-1).pointOfTime, trackingManager.progress.get(i).pointOfTime);
//    			}


//				MyRectangle rectangle = new MyRectangle((timeOfSnap/(double) totalWorktime)*500.0, 50.0, trackingManager.progress.get(i));
//				rectangle.setStroke(Color.BLACK);
//				hbox.getChildren().add(rectangle);
//			}

//    		return hbox;
//    	}
}









