package snmp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.value.ObjectIdentifierValue;

/**
 * A MibLoader
 * @author Helmuth Brunner
 * @version Oct 9, 2014
 * Current project: TestPackage
 */
public class Mibloader {

	/**
	 * Default-constructor
	 * don't use this
	 */
	public Mibloader() {
	}

	/**
	 * Loads the File + the parent directory
	 * @param file
	 * @return a Mib Object
	 */
	public Mib loadMib(File file) {

		MibLoader loader = new MibLoader();

		loader.addDir(file.getParentFile());
		try {
			return loader.load(file);
		} catch (IOException | MibLoaderException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to extract the Oids
	 * @param mib
	 * @return a Map with the Oids
	 */
	public HashMap extractOids(Mib mib) {
		HashMap map = new HashMap();
		Iterator iter = mib.getAllSymbols().iterator();
		MibSymbol symbol;
		MibValue value;

		while (iter.hasNext()) {
			symbol = (MibSymbol) iter.next();
			value = extractOid(symbol);
			if (value != null) {
				System.out.println(symbol.getName()+ " -|- " + value.getName());
				map.put(symbol.getName(), value);
			}
		}
		return map;
	}

	/**
	 * Extract the Oid from the given MibSymbol
	 * @param symbol
	 * @return the ObjectIdentifierValue
	 */
	public ObjectIdentifierValue extractOid(MibSymbol symbol) {
		MibValue  value;

		if (symbol instanceof MibValueSymbol) {
			value = ((MibValueSymbol) symbol).getValue();
			if (value instanceof ObjectIdentifierValue) {
				return (ObjectIdentifierValue) value;
			}
		}
		return null;
	}
}

