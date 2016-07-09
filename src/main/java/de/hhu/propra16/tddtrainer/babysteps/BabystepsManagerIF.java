package de.hhu.propra16.tddtrainer.babysteps;

public interface BabystepsManagerIF {
	
	/**
	 * 
	 * arguments = seconds
	 * enables/disables the Babysteps
	 * @param mPhaseTime The time until the {@link de.hhu.propra16.tddtrainer.catalog.Exercise} gets reset.
	 */
	public void start(int mPhaseTime);
	
	
}
