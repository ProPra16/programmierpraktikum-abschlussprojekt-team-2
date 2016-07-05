package de.hhu.propra16.tddtrainer.babysteps;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import de.hhu.propra16.tddtrainer.logic.PhaseManagerIF;

public class BabystepsManager implements BabystepsManagerIF{
	
	PhaseManagerIF phaseManager;
	private boolean status = false;
	private boolean running = false;
	private LocalDateTime startTime;
	private LocalDateTime nowTime;
	private boolean stopped = true;
	private int phaseTime;
	
	public BabystepsManager(PhaseManagerIF phaseManager) {
		this.phaseManager = phaseManager;
	}
	
	@Override
	public synchronized void start(int mPhaseTime) {
		if(this.status) {
			phaseTime = mPhaseTime;
			startTime = LocalDateTime.now();
			if(!running) {
				running = true;
				stopped = false;
				new Thread(() -> {
					while(!stopped) {
						nowTime = LocalDateTime.now();
						long dTime = nowTime.until(startTime, ChronoUnit.SECONDS);
		
			    		if(dTime > phaseTime) {
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
	}
	
	public void next() {
		
	}
	
	public void stop() {
		if(this.status) {
			this.stopped = true;
		}
	}
	
	public void enable(){
		this.status = true;
	}
	
	public void disable(){
		this.status = false;
	}
}
