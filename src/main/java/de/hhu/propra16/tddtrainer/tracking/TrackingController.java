package de.hhu.propra16.tddtrainer.tracking;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;



public class TrackingController {

    @FXML
    private BorderPane borderPane;

    
    @FXML
    private Label labelHeader;

    
    @FXML
    private Button buttonClose;

    
    @FXML
    private ListView<?> listViewCompilationResult;

    
    @FXML
    private ListView<?> listViewTestResult;

    
    @FXML
    private VBox vboxTestResult;

    
    @FXML
    private VBox vboxtracking;

    
    @FXML
    private VBox vboxCompilationResult;

    
    @FXML
    private Label labelTestResult;

    
    @FXML
    private Label labelCompilationResult;

    
}
