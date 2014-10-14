package snmp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import snmp.exceptions.KeyNotFoundException;
import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.value.ObjectIdentifierValue;

/**
 * One more try with mibble
 * 
 * The Mapping
 * 
 * @author Helmuth Brunner
 * @version Oct 14, 2014
 * Current project: TestPackage
 */
public class Mapping {

	// Attributes
	private String RES= "/res/";
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
		loader= new MibLoader();
		map= new HashMap<String, String>();
	}
	
	/**
	 * A Method which reads from a Mib-File
	 * @param file
	 */
	public void load(String filename) {
		
		URL u= Mapping.class.getResource(this.RES+ filename); // Loads the file which is in the res-folder
		
		file= new File(u.getPath());
		loader.addDir(file.getParentFile());
		
		try {
			mib= loader.load(file);
		} catch (IOException | MibLoaderException e) {
			e.printStackTrace();
		}
		
		i= mib.getAllSymbols().iterator();
		
		String oidString= "";
		while(i.hasNext()) {
			ms= (MibSymbol) i.next();
			oidString= this.getOID(ms);
			System.out.println("Name: "+ ms.getName() + "\nOID: " + oidString+ "\n");
			map.put(ms.getName(), oidString);
		}
	}
	
	/**
	 * Method which returns the OID for the given MibSymbol
	 * 
	 * for example:
	 * 
	 * 		teModuleServerFullCompliance = 1.3.6.1.2.1.122.2.2.4
	 * 
	 * @param msy the MibSymbol
	 * @return the OID to the Symbol as a String, if the String is "" there was no OID for the MibSymbol found
	 */
	public String getOID(MibSymbol msy) {
	
		MibValue  value;
		ObjectIdentifierValue oid;

		/* Example from http://www.mibble.org/doc/introduction.html */
		if (msy instanceof MibValueSymbol) {
			value = ((MibValueSymbol) msy).getValue();
			if (value instanceof ObjectIdentifierValue) {
				oid= (ObjectIdentifierValue) value;
				
				return oid.toString(); //The OID
			}
		}
		
		return ""; //If there was no OID founded
	}
	
	//TODO falsche Beschreibung der Methode
	/**
	 * Method to get the OID form the given mib
	 * @param key the mib
	 * @return the OID for the given mib
	 */
	public String readOID(String key) {
		if(map.get(key) == null) {
			throw new KeyNotFoundException();
		}
		return map.get(key);
	}
	
}
