package test.mapping;

import static org.junit.Assert.*;
import snmp.Mapping;
import snmp.exceptions.KeyNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test-Class for the Mapping Class
 * @author Helmuth Brunner
 * @version Oct 14, 2014
 * Current project: RTN
 */
public class TestMapping {
	
	private Mapping m;
	
	/**
	 * Setup-Method
	 */
	@Before
	public void setup() {
		m= new Mapping();
		m.load("APPLETALK-MIB"); // File must be in the res-folder
	}
	
	/**
	 * A test to test to get the OID
	 */
	@Test
	public void testgetOID() {
		
		assertEquals(m.readOID("aarp"), "1.3.6.1.2.1.13.2");
	
	}
	
	/**
	 * A test to test to get the OID with an incorrect key
	 */
	@Test(expected=KeyNotFoundException.class)
	public void testgetOIDNoKey() {
		
		assertEquals(m.readOID(""), "1.3.6.1.2.1.13.2");
	
	}
	
	/**
	 * A test to test if the filename for the mib-file is incorrect.
	 * Test won't be finished because in Mapping:63 System.exit(1) will be invoked
	 */
	@Test
	public void testIncorrectFilename() {
		
		m.load("APPLETALMIB");
	
	}

}
