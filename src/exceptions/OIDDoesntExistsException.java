package root.exceptions;

/**
 * 
 * 
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 * 
 */
public class OIDDoesntExistsException extends Exception {
	private static final long serialVersionUID = 3L;

	public OIDDoesntExistsException() {
		super("noSuchObject: OID does not exist!");
	}

	// TODO comments
	public OIDDoesntExistsException(String message) {
		super(message);
	}
}
