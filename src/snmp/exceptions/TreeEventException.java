package snmp.exceptions;

/**
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 */
public class TreeEventException extends Exception {

    //TODO comments
    public TreeEventException() {
        super("Error while getting the TreeEvent.");
    }

    //TODO comments
    public TreeEventException(String message) {
        super(message);
    }

}
