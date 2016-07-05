package de.hhu.propra16.tddtrainer.logic;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import com.google.common.eventbus.EventBus;

import de.hhu.propra16.tddtrainer.babysteps.BabystepsManager;
import de.hhu.propra16.tddtrainer.catalog.*;
import de.hhu.propra16.tddtrainer.gui.catalog.ExerciseSelector;
import de.hhu.propra16.tddtrainer.tracking.TrackingManager;

public class PhaseManagerTest {

	private PhaseManager phaseManager;
	private Exercise exerciseWithCompileError;
	private Exercise exerciseWithTestError;
	private Exercise exerciseWorking;
	
	@Before
	public void createPhaseManager() {
		phaseManager = new PhaseManager(new TrackingManager(), new ExerciseSelector(new FakeCatalogDatasource()), new EventBus());
		List<Exercise> fcd = new FakeCatalogDatasource().loadCatalog();
		exerciseWithCompileError = fcd.get(1);
		exerciseWithTestError = fcd.get(2);
		exerciseWorking = fcd.get(0);
	}
	
	@Test
	public void testCheckPhaseWithContinueOnPhase1() {
		assertEquals(Phase.GREEN, phaseManager.checkPhase(exerciseWithTestError, true).getPhase());
	}

	@Test
	public void testCheckPhaseWithContinueOnPhaseStatus1() {
		assertEquals(true, phaseManager.checkPhase(exerciseWithCompileError, true).isValid());
	}
	
	@Test
	public void testCheckPhaseWithContinueOnPhase2() {
		phaseManager.checkPhase(exerciseWithCompileError, true);
		assertEquals(Phase.GREEN, phaseManager.getPhase());
	}
	@Test
	public void testCheckPhaseWithContinueOnPhaseStatus2() {
		phaseManager.checkPhase(exerciseWithCompileError, true);
		assertEquals(false, phaseManager.checkPhase(exerciseWithCompileError, true).isValid());
	}
	@Test
	public void testCheckPhaseWithoutContinueOnPhaseStatus() {
		phaseManager.checkPhase(exerciseWithCompileError, false);
		assertEquals(Phase.RED, phaseManager.getPhase());
	}
	@Test
	public void testCheckPhaseWithContinueOnPhaseStatus3() {
		phaseManager.checkPhase(exerciseWithCompileError, true);
		phaseManager.checkPhase(exerciseWithTestError, true);
		assertEquals(Phase.GREEN, phaseManager.getPhase());
	}
	
	@Test
	public void testCheckPhaseOnRefactorPhase() {
		assertEquals(Phase.RED, phaseManager.getPhase());
		phaseManager.checkPhase(exerciseWithCompileError, true);
		assertEquals(Phase.GREEN, phaseManager.getPhase());
		phaseManager.checkPhase(exerciseWorking, true);
		assertEquals(Phase.REFACTOR, phaseManager.getPhase());
	}
	@Test
	public void testCheckPhaseOnRedPhase() {
		phaseManager.checkPhase(exerciseWithCompileError, true);
		phaseManager.checkPhase(exerciseWorking, true);
		phaseManager.checkPhase(exerciseWorking, true);
		assertEquals(Phase.RED, phaseManager.getPhase());
	}
}
