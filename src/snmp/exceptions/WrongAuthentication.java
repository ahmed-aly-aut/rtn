package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class WrongAuthentication extends Exception {
    private static final long serialVersionUID = 6L;


    public WrongAuthentication() {
        super("Wrong authentication used!");
    }

    //TODO comments
    public WrongAuthentication(String message) {
        super(message);
    }
}
