package graphic;


import commands.ActionCommand;
import commands.GetTableCommand;
import graphic.model.Input;
import graphic.view.GraphicOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Variable;
import snmp.*;
import snmp.exceptions.WrongAuthenticationException;
import snmp.exceptions.WrongSnmpVersionException;
import snmp.exceptions.WrongTransportProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The Main that runs every command of the graphic
 */
public class GraphicApp extends Application {

    public Stage primaryStage;
    private AnchorPane configLayout;
    private BorderPane rootLayout;
    private ObservableList<Input> addInput = FXCollections.observableArrayList();
    private ObservableList<Input> addInput2 = FXCollections.observableArrayList();
    private Vector<Vector<Variable>> v = null;

    public GraphicApp() {

        snmp();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Graphical User Interface");
    
        initRootLayout();
        
        showGraphicOverview();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GraphicApp.class.getResource("view/GraphicRoot.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the graphic overview inside the root layout.
     */
    public void showGraphicOverview() {
        try {
            // Load graphic overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GraphicApp.class.getResource("view/GraphicOverview.fxml"));
            AnchorPane graphicOverview = (AnchorPane) loader.load();

            // Set graphic overview into the center of root layout.
            rootLayout.setCenter(graphicOverview);

            // Give the controller access to the main app.
            GraphicOverviewController controller = loader.getController();
            controller.setGraphicApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showGraphicConfig(){
    	 try {
             // Load root layout from fxml file.
             FXMLLoader loader = new FXMLLoader();
             loader.setLocation(GraphicApp.class.getResource("view/GraphicConfig.fxml"));
             configLayout = (AnchorPane) loader.load();

             // Show the scene containing the root layout.
             Scene scene = new Scene(configLayout);
             primaryStage.setScene(scene);
             primaryStage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Input> getInput() {
        return addInput;
    }

    public ObservableList<Input> getInput2() {
        return addInput2;
    }

    public void snmp() {
        SnmpManager snmp;
        try {
            Authentication authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version2c, 6000, 3);
            Mapping mapping = new Mapping();
            mapping.load("/res/NS-POLICY.mib");
            snmp = new SnmpV2c(authentication, mapping);
            ActionCommand actionCommand = new ActionCommand(snmp);
            List<String> l = new ArrayList<String>();
            l.add("nsPlyId");
            l.add("nsPlyName");
            l.add("nsPlyServiceName");
            l.add("nsPlyService");
            l.add("nsPlySrcZone");
            l.add("nsPlyDstZone");
            l.add("nsPlySrcAddr");
            l.add("nsPlyDstAddr");
            l.add("nsPlyAction");
            l.add("nsPlyActiveStatus");
            GetTableCommand getTableCommand = new GetTableCommand(actionCommand, l);
            Vector<Vector<Variable>> v = getTableCommand.execute();
            for (int a = 0; a < v.get(0).size(); a++) {
                addInput.add(new Input(v.get(0).get(a).toString(), v.get(1).get(a).toString(), v.get(2).get(a).toString(), v.get(3).get(a).toString(),
                        v.get(4).get(a).toString(), v.get(5).get(a).toString(), v.get(6).get(a).toString(), v.get(7).get(a).toString(),
                        v.get(8).get(a).toString(), v.get(9).get(a).toString()));
            }
        } catch (WrongTransportProtocolException e1) {
            System.err.println(e1.getMessage());
            e1.printStackTrace();
        } catch (WrongAuthenticationException wrongAuthenticationException) {
            wrongAuthenticationException.printStackTrace();
        } catch (WrongSnmpVersionException wrongSnmpVersionException) {
            wrongSnmpVersionException.printStackTrace();
        }
    }

}