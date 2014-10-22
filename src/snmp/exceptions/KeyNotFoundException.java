package snmp.exceptions;

/**
 * A Exception which is used if the key isn't in the HashMap
 * <p/>
 * Extends RuntimeExcpetion is this a good solution????? //TODO think about it Helmuth
 *
 * @author Helmuth Brunner
 * @version Oct 14, 2014
 *          Current project: TestPackage
 */
public class KeyNotFoundException extends RuntimeException {

    /**
     * Default-Constructor
     */
    public KeyNotFoundException() {
        super("The given key was not found in the map");
    }

    /**
     * Constructor, with the option to set the error-message
     *
     * @param message the error-message
     */
    public KeyNotFoundException(String message) {
        super(message);
    }

}
