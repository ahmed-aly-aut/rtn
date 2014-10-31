package graphic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import graphic.GraphicApp;
import graphic.model.Input;

public class GraphicOverviewController {
    @FXML
    private TableView<Input> inputTable;
    @FXML
    private TableColumn<Input, String> plyIDColumn;
    @FXML
    private TableColumn<Input, String> plyServiceNameColumn;
    @FXML
    private TableColumn<Input, String> plySrcZoneColumn;
    @FXML
    private TableColumn<Input, String> plyNameColumn;
    

    // Reference to the main application.
    private GraphicApp graphicApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GraphicOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        plyIDColumn.setCellValueFactory(cellData -> cellData.getValue().plyIDProperty());
        plyServiceNameColumn.setCellValueFactory(cellData -> cellData.getValue().plyServiceNameProperty());
        plySrcZoneColumn.setCellValueFactory(cellData -> cellData.getValue().plySrcZoneProperty());
        plyNameColumn.setCellValueFactory(cellData -> cellData.getValue().plyNameProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setGraphicApp(GraphicApp graphicApp) {
        this.graphicApp = graphicApp;

        // Add observable list data to the table
        inputTable.setItems(graphicApp.getInput());
    }
    
    public void refreshButton(){
    	inputTable.getColumns().get(0).setVisible(false);
    	inputTable.getColumns().get(0).setVisible(true);
    	inputTable.setItems(graphicApp.getInput2());
    }
}