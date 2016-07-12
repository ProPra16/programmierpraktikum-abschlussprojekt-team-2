package PDFViewer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * shows the Handbook inside the application
 */
public class Handbook {
	/**
	 * shows the Handbook in PDF Viewer application 
	 */
	public void showPDF() {
		// PDF zeigen            		
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File("C:/Users/Benedikt/Documents/GitHub/programmierpraktikum-abschlussprojekt-team-2/Nutzerhandbuch.pdf");
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException e) {
		        // PDF Dateien können auf diesem Rechner nicht dargestellt werden
		    }
		}
	}
}
