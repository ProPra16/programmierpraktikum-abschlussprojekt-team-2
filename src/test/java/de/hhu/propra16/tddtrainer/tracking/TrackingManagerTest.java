package de.hhu.propra16.tddtrainer.tracking;

import static org.junit.Assert.*;
import org.junit.*;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import de.hhu.propra16.tddtrainer.executor.ExecutionResult;
import de.hhu.propra16.tddtrainer.executor.Executor;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public class TrackingManagerTest {

	TrackingManager trackingManager;
	
	@Before
	public void instantiateTrackingManager() {
		trackingManager = new TrackingManager();
		FakeCatalogDatasource fake = new FakeCatalogDatasource();
		Exercise exercise = fake.loadCatalog().get(0);
		Executor executor = new Executor();
		ExecutionResult result = executor.execute(exercise);
		
		PhaseStatus phaseStatus = new PhaseStatus(true, result, Phase.RED);
		PhaseStatus phaseStatus2 = new PhaseStatus(true, result, Phase.GREEN);
		try {
			Thread.sleep(1000);
			trackingManager.track(exercise, phaseStatus);
			Thread.sleep(2000);
			trackingManager.track(exercise, phaseStatus2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1(){
		long timeOfFirstSnap = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(0).pointOfTime);
		assertEquals(1, timeOfFirstSnap, 0.1);
	}
	@Test
	public void test2() {
		long timeBetweenSnaps = TrackingManager.getTimeBetweenSnaps(trackingManager.progress.get(0).pointOfTime, trackingManager.progress.get(1).pointOfTime);
		assertEquals(2, timeBetweenSnaps, 0.1);
	}
	@Test
	public void test3() {
		long totalWorkTime = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(1).pointOfTime);
		assertEquals(3, totalWorkTime, 0.1);
	}
}
