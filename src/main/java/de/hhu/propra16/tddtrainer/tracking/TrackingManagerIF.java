package de.hhu.propra16.tddtrainer.tracking;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public interface TrackingManagerIF {
	
	public void track(Exercise exercise, PhaseStatus phaseStatus);

}
