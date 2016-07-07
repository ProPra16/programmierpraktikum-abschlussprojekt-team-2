package de.hhu.propra16.tddtrainer.tracking;

import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

	Snapshot snapshot;
	
    public MyRectangle(double width, double height, Snapshot snapshot) {
    	
       super(width, height, snapshot.getColor());
       this.snapshot = snapshot;
        
        setOnMouseClicked(event -> {
        	Trackline.listViewCompile.getItems().clear();
        	Trackline.listViewTest.getItems().clear();
        	
        	for(String result : snapshot.compilationResultToList()) {
        		Trackline.listViewCompile.getItems().add(result);
        	}
        	for(String result : snapshot.testResultToList()) {
        		Trackline.listViewTest.getItems().add(result);
        	}
        });
    }
}

