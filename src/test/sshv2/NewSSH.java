package test.sshv2;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.Result;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecCommand;
import ssh.SSHConnector;

/**
 * A new SSH-Class
 * 
 * Not jet tested
 * 
 * @author Helmuth Brunner
 * @version Oct 31, 2014
 * Current project: RTN
 */
public class NewSSH {

	private ConnBean cb;
	
	private String username, password, ip;
	private SSHExec ssh;
	
	/**
	 * Constructor
	 * @param username the username on the remote system
	 * @param password the password for the username
	 * @param ip the ip address
	 */
	public NewSSH(String username, String password, String ip) {
		this.username= username;
		this.password= password;
		this.ip= ip;
	}
	
	/**
	 * Creates the connection to the remote system
	 */
	public void connect() {
		cb= new ConnBean(this.ip, this.username, this.password);
		ssh= SSHExec.getInstance(cb);
		ssh.connect();
		
	}
	
	/**
	 * Method to execute a command
	 * @param command the command
	 * @return the out from the command
	 */
	public String exec(String command) {
		CustomTask sampleTask = new ExecCommand(command);
		Result res;
		try {
			ssh.exec(sampleTask);
			res= ssh.exec(sampleTask);
			
			if (res.isSuccess) {
			    return res.sysout;
			}
			else {
			    return res.error_msg;
			}
			
		} catch (TaskExecFailException e) {
			System.err.println("in exec");
		}
		
		return "succesful";
		
	}
	
	/**
	 * Disconnects the ssh-client from the sever
	 * @return true if successful false if it failed
	 */
	public Boolean disconnect() {
		return ssh.disconnect();
	}
	
}
