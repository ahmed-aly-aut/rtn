package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class WrongTransportProtocolException extends Exception {
    private static final long serialVersionUID = 2L;


    public WrongTransportProtocolException() {
        super("Wrong transport protocol. Must be TCP or UDP!");
    }

    //TODO comments
    public WrongTransportProtocolException(String message) {
        super(message);
    }
}
