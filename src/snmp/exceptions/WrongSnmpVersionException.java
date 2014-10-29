package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class WrongSnmpVersionException extends Exception {
    private static final long serialVersionUID = 5L;

    public WrongSnmpVersionException() {
        super("Wrong Snmp version");
    }

    public WrongSnmpVersionException(String message) {
        super(message);
    }
}
