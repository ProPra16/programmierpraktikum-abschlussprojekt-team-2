package de.hhu.propra16.tddtrainer.babysteps;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public interface BabystepsManagerIF {
	
	/**
	 * 
	 * arguments = seconds
	 * enables/disables the Babysteps
	 * @return void
	 */
	public void start(int mPhaseTime);
	
	
}
