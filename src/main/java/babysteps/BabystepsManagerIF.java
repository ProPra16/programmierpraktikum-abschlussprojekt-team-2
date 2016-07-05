package babysteps;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public interface BabystepsManagerIF {
	
	/**
	 * 
	 * enables/disables the Babysteps
	 * @return void
	 */
	public void activator(int mPhaseTime_GREEEN, int mPhaseTime_RED);
	
	/**
	 * 
	 * checks if progress is in time
	 * @return returns the current valid Exercise
	 */
	public Exercise check(Exercise mValidExercise, Exercise mExercise, PhaseStatus mPhaseStatus);
	
}
