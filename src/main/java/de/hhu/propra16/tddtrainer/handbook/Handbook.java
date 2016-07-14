package de.hhu.propra16.tddtrainer.handbook;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
		        File myFile = new File("Nutzerhandbuch.pdf");
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	System.err.println("PDF Dateien koennen auf diesem Rechner nicht dargestellt werden");
		    }
		} else {
			System.err.println("Desktop.isDesktopSupported() = false");
		}
	}
}
