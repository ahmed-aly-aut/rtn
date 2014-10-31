package graphic.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for the Input.
 *
 * @author 
 */
public class Input {

    private final StringProperty PlyID;
    private final StringProperty PlyName;
    private final StringProperty PlyService;
    private final StringProperty PlyServiceName;
    private final StringProperty PlySrcZone;
    private final StringProperty PlyDstZone;
    private final StringProperty PlySrcAddr;
    private final StringProperty PlyDstAddr;
    private final StringProperty PlyAction;
    private final StringProperty PlyActiveStatus;
    
    private String ip;
    private String port;
    private String sshuname;
    private String sshpass;
    private String timeint;
    private Boolean v2cbttn;
    private Boolean v3bttn;
    


    /**
     * Default constructor.
     */
    public Input(String PlyID,String PlyName, String PlyService, String PlyServiceName, String PlySrcZone,
    		String PlyDstZone, String PlySrcAddr, String PlyDstAddr, String PlyAction, String PlyActiveStatus) {
    	this.PlyID = new SimpleStringProperty(PlyID);
    	this.PlyName = new SimpleStringProperty(PlyName);
    	this.PlyService = new SimpleStringProperty(PlyService);
    	this.PlyServiceName = new SimpleStringProperty(PlyServiceName);
    	this.PlySrcZone = new SimpleStringProperty(PlySrcZone);
    	this.PlyDstZone = new SimpleStringProperty(PlyDstZone);
    	this.PlySrcAddr = new SimpleStringProperty(PlySrcAddr);
    	this.PlyDstAddr = new SimpleStringProperty(PlyDstAddr);
    	this.PlyAction = new SimpleStringProperty(PlyAction);
    	this.PlyActiveStatus = new SimpleStringProperty(PlyActiveStatus);
    	
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
    
    
    public String getPlyName(){
    	return PlyName.get();
    }
    
    public void setPlyName(String PlyName){
    	this.setPlyName(PlyName);
    }
    
    public StringProperty plyNameProperty(){
    	return PlyName;
    }   
    
    
    public String getPlyService(){
    	return PlyName.get();
    }
    
    public void setPlyService(String PlyService){
    	this.setPlyService(PlyService);
    }
    
    public StringProperty plyServiceProperty(){
    	return PlyService;
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
    
    
    public String getPlyDstZone(){
    	return PlyDstZone.get();
    }
    
    public void setPlyDstZone(String PlyDstZone){
    	this.setPlyDstZone(PlyDstZone);
    }
    
    public StringProperty plyDstZoneProperty(){
    	return PlyDstZone;
    }   
    
    
    public String getPlySrcAddr(){
    	return PlySrcAddr.get();
    }
    
    public void setPlySrcAddr(String PlySrcAddr){
    	this.setPlySrcAddr(PlySrcAddr);
    }
    
    public StringProperty plySrcAddrProperty(){
    	return PlySrcAddr;
    }
    
    
    public String getPlyDstAddr(){
    	return PlyDstAddr.get();
    }
    
    public void setPlyDstAddr(String PlyDstAddr){
    	this.setPlyDstAddr(PlyDstAddr);
    }
    
    public StringProperty plyDstAddrProperty(){
    	return PlyDstAddr;
    }   
    
    
    public String getPlyAction(){
    	return PlyAction.get();
    }
    
    public void setPlyAction(String PlyAction){
    	this.setPlyAction(PlyAction);
    }
    
    public StringProperty plyActionProperty(){
    	return PlyAction;
    }
    
    
    public String getPlyActiveStatus(){
    	return PlyActiveStatus.get();
    }
    
    public void setPlyActiveStatus(String PlyActiveStatus){
    	this.setPlyActiveStatus(PlyActiveStatus);
    }
    
    public StringProperty plyActiveStatusProperty(){
    	return PlyActiveStatus;
    }   
    
    
    public String getIP(){
    	return ip;
    }
    
    public void setIP(String ip){
    	this.setIP(ip);
    }
    
    
    public String getPort(){
    	return port;
    }
    
    public void setPort(String Port){
    	this.setPort(Port);
    }
    
    
    public String getSSHUname(){
    	return sshuname;
    }
    
    public void setSSHUname(String sshuname){
    	this.setSSHUname(sshuname);
    }
    
    
    public String getSSHPass(){
    	return sshpass;
    }
    
    public void setSSHPass(String sshpass){
    	this.setSSHPass(sshpass);
    }
    
    
    public String getTimeint(){
    	return timeint;
    }
    
    public void setTimeint(String timeint){
    	this.setTimeint(timeint);
    }
    
    
    public Boolean getsV2cBttn(){
    	return v2cbttn;
    }
    
    public void setV2cBttn(boolean v2cbttn){
    	this.setV2cBttn(v2cbttn);
    }
    
    
    public Boolean getV3Bttn(){
    	return v3bttn;
    }
    
    public void setV3Bttn(boolean v3bttn){
    	this.setV3Bttn(v3bttn);
    }
}