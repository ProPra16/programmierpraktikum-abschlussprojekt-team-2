package de.hhu.propra16.tddtrainer.tracking;

import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

	Snapshot snapshot;
	private TrackingController controller;
	
    public MyRectangle(double width, double height, Snapshot snapshot) {
    	
       super(width, height, snapshot.getColor());
       this.snapshot = snapshot;
        
        setOnMouseClicked(event -> {
        	controller.listViewCompilationResult.getItems().clear();
        	controller.listViewTestResult.getItems().clear();
        	
        	for(String result : snapshot.compilationResultToList()) {
        		controller.listViewCompilationResult.getItems().add(result);
        	}
        	for(String result : snapshot.testResultToList()) {
        		controller.listViewTestResult.getItems().add(result);
        	}
        });
    }

	public void setController(TrackingController trackingController) {
		this.controller = trackingController;
	}
}

