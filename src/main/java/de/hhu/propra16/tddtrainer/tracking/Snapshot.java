package de.hhu.propra16.tddtrainer.tracking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.executor.CompilationResult;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.scene.paint.Color;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;

public class Snapshot {

	Exercise exercise;
	LocalDateTime pointOfTime;
	PhaseStatus phaseStatus;

	public Snapshot(Exercise exercise, LocalDateTime pointOfTime, PhaseStatus phaseStatus) {
		this.exercise = exercise; 
		this.pointOfTime = pointOfTime;
		this.phaseStatus = phaseStatus;
	}
	
	Color getColor(){
	    	switch(phaseStatus.getPhase()) {
	    	case RED:
	    		return Color.CRIMSON;
	    	case GREEN:
	    		return Color.FORESTGREEN;
	    	default:
	    		return Color.web("#6f8391");
	    		
	    	}
	}
}
