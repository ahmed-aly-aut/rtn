package graphic.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for the Input.
 *
 * @author 
 */
public class Input {

    private final StringProperty PlyID;
    private final StringProperty PlyServiceName;
    private final StringProperty PlySrcZone;
    private final StringProperty PlyName;


    /**
     * Default constructor.
     */
    public Input(String PlyID, String PlyServiceName, String PlySrcZone, String PlyName) {
    	this.PlyID = new SimpleStringProperty(PlyID);
    	this.PlyServiceName = new SimpleStringProperty(PlyServiceName);
    	this.PlySrcZone = new SimpleStringProperty(PlySrcZone);
    	this.PlyName = new SimpleStringProperty(PlyName);
    }

    public String getPlyID(){
    	return PlyID.get();
    }
    
    public void setPlyID(int PlyID){
    	this.setPlyID(PlyID);
    }
    
    public StringProperty plyIDProperty(){
    	return PlyID;
    }
    
    
    public String getPlyServiceName(){
    	return PlyServiceName.get();
    }
    
    public void setPlyServiceName(String PlyServiceName){
    	this.setPlyServiceName(PlyServiceName);
    }
    
    public StringProperty plyServiceNameProperty(){
    	return PlyServiceName;
    }
    
    
    public String getPlySrcZone(){
    	return PlySrcZone.get();
    }
    
    public void setPlySrcZone(String PlySrcZone){
    	this.setPlySrcZone(PlySrcZone);
    }
    
    public StringProperty plySrcZoneProperty(){
    	return PlySrcZone;
    }
    
    
    public String getPlyName(){
    	return PlyName.get();
    }
    
    public void setPlyName(String PlyName){
    	this.setPlyName(PlyName);
    }
    
    public StringProperty plyNameProperty(){
    	return PlyName;
    }   
    
    
    
    
    
    
    
    
    
}