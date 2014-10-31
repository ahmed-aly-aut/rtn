package commands;

import ssh.SSHConnector;
import ssh.SSHManager;
import content.Base;

public class SetPolicy {

	private Base base= Base.get();
	private SSHConnector connector;
	private SSHManager manager; 
	
	public SetPolicy() {
		
		// String userName, String password, String connectionIP, String knownHostsFileName, int connectionPort, int timeOut
		
		connector= new SSHConnector(base.getSettingString("username"), base.getSettingString("password"), base.getSettingString("ip"), base.getSettingString("pathto"));

		manager = new SSHManager(connector);
	}
	
	public void setPolicy() {
		
		System.out.println("SetPolicy");
		System.out.println( manager.sendCommand("set policy") );
		// use the command "get policy" to list all policies
		
	}
}
