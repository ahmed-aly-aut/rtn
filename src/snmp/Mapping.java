package snmp;

import net.percederberg.mibble.*;
import net.percederberg.mibble.value.ObjectIdentifierValue;
import org.apache.log4j.Logger;
import snmp.exceptions.KeyNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A Class which provides the functions to read a Mib-File
 * <p/>
 * This class uses the Mibble library
 *
 * @author Helmuth Brunner
 * @version Oct 14, 2014
 *          Current project: RTN
 */
public class Mapping {

    // Attributes
    private Logger log = Logger.getLogger(Mapping.class.getName());

    private String RES = "/res/";
    private File file;

    private MibLoader loader;
    private Mib mib;
    private MibSymbol ms;

    private HashMap<String, String> map;
    private Iterator i;

    /**
     * Private-Constructor for the Singleton Pattern.
     */
    public Mapping() {
        loader = new MibLoader();
        map = new HashMap<String, String>();
    }

    /**
     * A Method which reads from a Mib-File
     * The Mib-File has to be stored in the res-folder
     *
     * @param filename the filename from the Mib-File
     */
    public void load(String filename) {

        URL u = Mapping.class.getResource(this.RES + filename); // Loads the file which is in the res-folder

        if (u == null) {
            log.info("The filename: \"" + filename + "\" was not found.\tSystem.exit(1) will be invoked.");
            System.exit(1); //TODO think about this line
        }

        file = new File(u.getPath());

        loader.addDir(file.getParentFile());

        try {
            mib = loader.load(file);
        } catch (IOException ipe) {
            log.error(ipe);
        } catch (MibLoaderException mible) {
            Iterator itr = mible.getLog().entries();
            while (itr.hasNext()) {
                MibLoaderLog.LogEntry element = (MibLoaderLog.LogEntry) itr.next();
                log.error(element.getFile().getName() + ":"
                        + element.getLineNumber() + ": "
                        + element.getMessage() + " ");
            }
            //mible.getLog().printTo(System.err);
            log.error(mible);
        }

        i = mib.getAllSymbols().iterator();

        String oidString = "";
        while (i.hasNext()) {
            ms = (MibSymbol) i.next();
            oidString = this.getOID(ms);

            map.put(ms.getName(), oidString);
        }
    }

    /**
     * Method which returns the OID for the given MibSymbol
     * <p/>
     * for example:
     * <p/>
     * teModuleServerFullCompliance = 1.3.6.1.2.1.122.2.2.4
     *
     * @param msy the MibSymbol
     * @return the OID to the Symbol as a String, if the String is "" there was no OID for the MibSymbol found
     */
    public String getOID(MibSymbol msy) {

        MibValue value;
        ObjectIdentifierValue oid;

		/* Example from http://www.mibble.org/doc/introduction.html */
        if (msy instanceof MibValueSymbol) {
            value = ((MibValueSymbol) msy).getValue();
            if (value instanceof ObjectIdentifierValue) {
                oid = (ObjectIdentifierValue) value;

                return oid.toString(); //The OID
            }
        }
        log.debug("No OID was founded for the Mib= " + msy.getName());
        return ""; //If there was no OID founded
    }

    //TODO falsche Beschreibung der Methode

    /**
     * Method to get the OID from the given mib
     *
     * @param key the mib
     * @return the OID for the given mib
     */
    public String readOID(String key) {
        if (map.get(key) == null) {
            log.info("KeyNotFoundException will be thrown!");
            throw new KeyNotFoundException();
        }
        return map.get(key);
    }

}
