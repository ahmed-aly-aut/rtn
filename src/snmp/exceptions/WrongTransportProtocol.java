package snmp.exceptions;

/**
 * 
 * 
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 * 
 */
public class WrongTransportProtocol extends Exception {
private static final long serialVersionUID = 2L;

	
	public WrongTransportProtocol() {
		super("Wrong transport protocol. Must be TCP or UDP!");
	}

	//TODO comments
	public WrongTransportProtocol(String message) {
		super(message);
	}
}
