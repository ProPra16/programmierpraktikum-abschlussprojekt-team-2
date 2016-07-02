package de.hhu.propra16.tddtrainer.tracking;

import java.time.LocalDateTime;
import java.util.ArrayList;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public class TrackingManager implements TrackingManagerIF{

	ArrayList<Snapshot> progress;
	LocalDateTime start;
	
	
	public TrackingManager() {
		progress = new ArrayList<>();
		start = LocalDateTime.now(); 
	}

	@Override
	public void track(Exercise exercise, PhaseStatus phaseStatus) {
		Snapshot snapshot = new Snapshot(exercise, LocalDateTime.now(),  phaseStatus);
		progress.add(snapshot);
		
	}
	
	public void displayInNewWindow() {
		Trackline.popup(this);
	}
	
}
