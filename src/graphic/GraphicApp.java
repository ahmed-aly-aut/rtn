package graphic;


import graphic.model.Input;
import graphic.view.GraphicOverviewController;

import java.io.IOException;

import commands.ActionCommand;
import commands.GetTableCommand;

import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Variable;

import snmp.*;
import snmp.exceptions.*;
import ssh.SSHConnector;
import ssh.SSHManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** The Main that runs every command of the graphic 
 * 
 */
public class GraphicApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Input> addInput = FXCollections.observableArrayList();
    private ObservableList<Input> addInput2 = FXCollections.observableArrayList();
    
    public GraphicApp(){
    	for(int i = 0; i<10;i++){
    		addInput.add(new Input("53"+i,"ANY", "Trust", "P46P"+i,"1","2","3","4","5","6"));
    	}
    	for(int j = 10; j<20;j++){
    		addInput2.add(new Input("53"+j,"ANY", "Trust", "P46P"+j,"1","2","3","4","5","6"));
    	}
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

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ObservableList<Input> getInput(){
    	return addInput;
    }
    
    public ObservableList<Input> getInput2(){
    	return addInput2;
    }
    
    public static void main(String[] args) {
        launch(args);
//        new GraphicApp().snmp();
    }
    
//    public void snmp() {
//        SnmpManager snmp;
//        try {
//            Authentication authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version2c, 6000, 3);
//            Mapping mapping = new Mapping();
//            //mapping.load("NETSCREEN-SMI.mib");
//            mapping.load("NS-POLICY.mib");
//            snmp = new SnmpV2c(authentication, mapping);
//            ActionCommand actionCommand = new ActionCommand(snmp);
//            List<String> l = new ArrayList<String>();
//            l.add("nsPlyId");
//            l.add("nsPlyServiceName");
//            l.add("nsPlySrcZone");
//            l.add("nsPlyName");
//            GetTableCommand getTableCommand = new GetTableCommand(actionCommand, l);
//            System.out.println(getTableCommand.execute());
//            for (Vector<Variable> v : getTableCommand.execute())
//                for (Variable v2 : v)
//                    System.out.println(v2);
////            for()
//        } catch (WrongTransportProtocolException e1) {
//            System.err.println(e1.getMessage());
//            e1.printStackTrace();
//        } catch (WrongAuthenticationException wrongAuthenticationException) {
//            wrongAuthenticationException.printStackTrace();
//        } catch (WrongSnmpVersionException wrongSnmpVersionException) {
//            wrongSnmpVersionException.printStackTrace();
//        }
//    }
//
//    public void ssh() {
//        SSHConnector connector = new SSHConnector("aaly", "Aly1234", "10.0.2.15",
//                "/home/aaly/.ssh/known_hosts");
//        SSHManager ssh = new SSHManager(connector);
//        System.out.println("connected");
//        System.out.println(ssh.sendCommand("cat test"));
//        System.out.println("Connection closed");
//    }
//
//    public void mibble() {
//        Mapping mapping = new Mapping();
//        //mapping.load("NETSCREEN-SMI.mib");
//        mapping.load("NS-POLICY.mib");
//        System.out.println(mapping.readOID("nsPlyId"));
//    }
}