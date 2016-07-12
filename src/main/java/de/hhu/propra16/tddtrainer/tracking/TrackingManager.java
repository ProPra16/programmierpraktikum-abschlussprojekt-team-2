package de.hhu.propra16.tddtrainer.tracking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.common.eventbus.Subscribe;

import de.hhu.propra16.tddtrainer.Main;
import de.hhu.propra16.tddtrainer.catalog.Exercise;
import de.hhu.propra16.tddtrainer.events.LanguageChangeEvent;
import de.hhu.propra16.tddtrainer.logic.PhaseStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrackingManager implements TrackingManagerIF {

	ArrayList<Snapshot> progress;
	LocalDateTime start;
	ResourceBundle bundle;

	public TrackingManager() {
		progress = new ArrayList<>();
		start = LocalDateTime.now();
	}

	@Override
	public void track(Exercise exercise, PhaseStatus phaseStatus) {
		Snapshot snapshot = new Snapshot(exercise, LocalDateTime.now(), phaseStatus);
		progress.add(snapshot);
	}

	static long getTimeBetweenSnaps(LocalDateTime start, LocalDateTime end) {
		return start.until(end, ChronoUnit.SECONDS);
	}

	public void displayInNewWindow() {
		
		Stage stage = new Stage();
		stage.setMinWidth(500);
		stage.setMinHeight(380);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Tracked Progress");
		
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
			loader.setLocation(Main.class.getResource("gui/tracking/Tracking.fxml"));
			BorderPane boarderPane = (BorderPane) loader.load();
			
			TrackingController controller = loader.getController();
			controller.generateTrackline(this);
			controller.setStage(stage);
			
			Scene scene = new Scene(boarderPane);
	        stage.setScene(scene);
	        stage.showAndWait();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void reset() {
		progress = new ArrayList<>();
		start = LocalDateTime.now();
	}
	
	@Subscribe
	public void setResourceBundle(LanguageChangeEvent event) {
		this.bundle = event.getBundle();
	}
}
