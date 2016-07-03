package de.hhu.propra16.tddtrainer.events;

import de.hhu.propra16.tddtrainer.catalog.Exercise;

public class NewExerciseEvent {
	
	private Exercise exercise;

	public NewExerciseEvent(Exercise exercise) {
		this.exercise = exercise;
	}

	public Exercise getExercise() {
		return exercise;
	}
}
