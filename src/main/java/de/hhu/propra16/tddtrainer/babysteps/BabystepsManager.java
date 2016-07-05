package de.hhu.propra16.tddtrainer.babysteps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

/**
 * 
 * PhaseManager - Methode: checkPhase - als erstes babysteps.check(validExercise, exercise, phaseStatus);
 *	
 * Exercise SelectorController
 * 		benötigt Button / 2 x Input für RED & GREEN Time
 * 	
 * 	RootLayoutController 
 * 		
 * 		bentötigt Alert o.ä. optisches Feedback für Reset durch Timeout
 *
 */


public class BabystepsManager extends Thread implements BabystepsManagerIF{
	
	private boolean status = false;
	private int phaseTime_RED = 0;	
	private int phaseTime_GREEN = 0;
	private LocalDateTime startTime;
	private LocalDateTime nowTime;
	private String errorMsg = "Reset timeout";
	
	public BabystepsManager() {}
	
	@Override
	public Exercise check(Exercise mValidExercise, Exercise mExercise, PhaseStatus mPhaseStatus) {
		nowTime = LocalDateTime.now();
		long dTime = nowTime.until(startTime, ChronoUnit.MINUTES);
		
		switch(mPhaseStatus.getPhase()) {
    	case RED:
    		if(dTime > phaseTime_RED) return mValidExercise;
    		return mExercise;
    	case GREEN:
    		if(dTime > phaseTime_GREEN) return mValidExercise;
    		return mExercise;
    	default:
    		return mExercise;
    	}
	}
	
	public void start() {
		startTime = LocalDateTime.now();
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	@Override
	public void activator(int mPhaseTime_GREEN, int mPhaseTime_RED) {
		changeStatus();
		this.phaseTime_GREEN = mPhaseTime_GREEN;
		this.phaseTime_RED = mPhaseTime_RED;
		start();
		
	}
	
	public void changeStatus() {
		if (this.status == false) {
			this.status = true;
			return;
		}
		this.status = false;
	}
		
}
