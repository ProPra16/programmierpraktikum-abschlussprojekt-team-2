package de.hhu.propra16.tddtrainer.tracking;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.*;

import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.catalog.FakeCatalogDatasource;
import de.hhu.propra16.tddtrainer.executor.ExecutionResult;
import de.hhu.propra16.tddtrainer.executor.Executor;
import de.hhu.propra16.tddtrainer.logic.Phase;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;

public class TrackingManagerTest {

	TrackingManager trackingManager;
	Exercise exercise;
	PhaseStatus phaseStatus;
	PhaseStatus phaseStatus2;
	
	@Before
	public void instantiateTrackingManager() {
		trackingManager = new TrackingManager();
		FakeCatalogDatasource fake = new FakeCatalogDatasource();
		exercise = fake.loadCatalog().get(0);
		Executor executor = new Executor();
		ExecutionResult result = executor.execute(exercise);
		phaseStatus = new PhaseStatus(true, result, Phase.RED);
		phaseStatus2 = new PhaseStatus(true, result, Phase.GREEN);
	}
	
	@Test
	public void constructorTest(){
		LocalDateTime now = LocalDateTime.now();
		long time = trackingManager.start.until(now, ChronoUnit.SECONDS);
		assertEquals(0, time, 1);
	}
	
	@Test
	public void trackTest() {
		LocalDateTime now = LocalDateTime.now();
		trackingManager.track(exercise, phaseStatus);
		assertEquals(now, trackingManager.progress.get(0).pointOfTime);
	}
	
	@Test
	public void timeTest1(){
		try {
			Thread.sleep(1000);
			trackingManager.track(exercise, phaseStatus);
			Thread.sleep(2000);
			trackingManager.track(exercise, phaseStatus2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long timeOfFirstSnap = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(0).pointOfTime);
		assertEquals(1, timeOfFirstSnap, 0.1);
	}
	@Test
	public void timeTest2() {
		try {
			Thread.sleep(1000);
			trackingManager.track(exercise, phaseStatus);
			Thread.sleep(2000);
			trackingManager.track(exercise, phaseStatus2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long timeBetweenSnaps = TrackingManager.getTimeBetweenSnaps(trackingManager.progress.get(0).pointOfTime, trackingManager.progress.get(1).pointOfTime);
		assertEquals(2, timeBetweenSnaps, 0.1);
	}
	@Test
	public void timeTest3() {
		try {
			Thread.sleep(1000);
			trackingManager.track(exercise, phaseStatus);
			Thread.sleep(2000);
			trackingManager.track(exercise, phaseStatus2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long totalWorkTime = TrackingManager.getTimeBetweenSnaps(trackingManager.start, trackingManager.progress.get(1).pointOfTime);
		assertEquals(3, totalWorkTime, 0.1);
	}
}
