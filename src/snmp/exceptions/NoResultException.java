package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class NoResultException extends Exception {
    private static final long serialVersionUID = 3L;

    public NoResultException() {
        super("No result returned!");
    }

    // TODO comments
    public NoResultException(String message) {
        super(message);
    }
}
