package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class OIDDoesNotExistsException extends Exception {
    private static final long serialVersionUID = 3L;

    public OIDDoesNotExistsException() {
        super("noSuchObject: OID does not exist!");
    }

    // TODO comments
    public OIDDoesNotExistsException(String message) {
        super(message);
    }
}
