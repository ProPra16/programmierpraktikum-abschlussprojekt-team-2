package de.hhu.propra16.tddtrainer.babysteps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;

public class BabystepsManager implements BabystepsManagerIF{
	
	PhaseManagerIF phaseManager;
	private boolean running = false;
	private LocalDateTime startTime;
	private LocalDateTime nowTime;
	private boolean stopped = true;
	
	public BabystepsManager(PhaseManagerIF phaseManager) {
		this.phaseManager = phaseManager;
	}
	
	@Override
	public synchronized void start(int mPhaseTime) {
		if(!running) {
			running = true;
			stopped = false;
			startTime = LocalDateTime.now();
			new Thread(() -> {
				while(!stopped) {
					nowTime = LocalDateTime.now();
					long dTime = nowTime.until(startTime, ChronoUnit.SECONDS);
	
		    		if(dTime > mPhaseTime) {
						phaseManager.resetPhase();
						running = false;
						return;
		    		};
					
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			})
			.start();
		}
	}
	
	public void stop() {
		this.stopped = true;
	}
}
