package graphic.view;
import graphic.GraphicApp;
import graphic.model.Input;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;





/**
 * Dialog to edit details of a person.
 * 
 * 
 */
public class GraphicConfigController {

    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private TextField sshuserField;
    @FXML
    private PasswordField sshpassField;
    @FXML
    private RadioButton v2cButton;
    @FXML
    private RadioButton v3Button;
    @FXML
    private TextField timeintField;
    
    // Reference to the main application.
    private GraphicApp graphicApp;
    private Input input;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GraphicConfigController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
//    private void setInput(Input input){
//    	ipField.setText(this.input.getIP());
//        portField.setText(this.input.getPort());
//        sshuserField.setText(this.input.getSSHUname());
//        sshpassField.setText(this.input.getSSHPass());
//        if(v2cButton.isSelected() == true){
//        	this.input.setV2cBttn(true);
//        }else{
//        	this.input.setV3Bttn(true);
//        }
//        
//        
//        this.input = input;
//    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param graphicApp
     */
    public void setGraphicApp(GraphicApp graphicApp) {
        this.graphicApp = graphicApp;
    }

    public void confirmButton(){
    	this.graphicApp.start(this.graphicApp.primaryStage);
    }
    
}
