package test.mapping;

import static org.junit.Assert.*;
import snmp.Mapping;

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
	
	@Before
	public void setup() {
		m= new Mapping();
		m.load("APPLETALK-MIB"); // File must be in the res-folder
	}
	
	/**
	 * A Test to test to get the OID
	 */
	@Test
	public void testgetOID() {
		
		assertEquals(m.readOID("aarp"), "1.3.6.1.2.1.13.2");
	
	}

}
