package de.hhu.propra16.tddtrainer.tracking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
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
	
	static long getTimeBetweenSnaps(LocalDateTime start, LocalDateTime end) {
		return start.until(end, ChronoUnit.SECONDS);	
	}
	
	public void displayInNewWindow() {
		Trackline.popup(this);
	}
	
	public void reset() {
		progress = new ArrayList<>();
		start = LocalDateTime.now();
	}
}
