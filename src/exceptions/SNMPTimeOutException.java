package root.exceptions;

/**
 * 
 * 
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 * 
 */
public class SNMPTimeOutException extends Exception {
	private static final long serialVersionUID = 1L;

	
	public SNMPTimeOutException() {
		super("Timeout!");
	}

	//TODO comments
	public SNMPTimeOutException(String message) {
		super(message);
	}

}
