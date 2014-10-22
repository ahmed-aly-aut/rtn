package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class WrongSnmpVersion extends Exception {
    private static final long serialVersionUID = 5L;

    public WrongSnmpVersion() {
        super("Wrong Snmp version");
    }

    public WrongSnmpVersion(String message) {
        super(message);
    }
}
