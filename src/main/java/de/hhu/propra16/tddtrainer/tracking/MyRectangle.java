package de.hhu.propra16.tddtrainer.tracking;


import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

	Snapshot snapshot;
	
    public MyRectangle(double width, double height, Snapshot snapshot) {
    	
       super(width, height, snapshot.getColor());
       this.snapshot = snapshot;
        
        setOnMouseClicked(event -> {
        	String output = "The executionResult at this time was: \n";
        	output += snapshot.executionResultToList().get(0) + "\n";
        	output += snapshot.executionResultToList().get(1);
            Trackline.snapInfo.setText(output);
        });
        						//Entweder er bekommt hier die Fehlermeldungen
        						//oder er bekommt die Möglichkeit an die Stelle zurückzukehren...
    }
}

