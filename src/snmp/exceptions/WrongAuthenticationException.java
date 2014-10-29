package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class WrongAuthenticationException extends Exception {
    private static final long serialVersionUID = 6L;


    public WrongAuthenticationException() {
        super("Wrong authentication used!");
    }

    //TODO comments
    public WrongAuthenticationException(String message) {
        super(message);
    }
}
