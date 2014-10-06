package content.test;

import static org.junit.Assert.*;
import org.junit.Test;
import content.Base;

/**
 * A test class to test the Base-Class
 * @author Helmuth Brunner
 * @version Oct 6, 2014
 * Current project: RTN
 */
public class TestContent {

	// Attribute
	private static Base base= Base.get();
	
	/**
	 * Reads a text from the settingsfile
	 * 
	 * Test: successful 
	 */
	@Test
	public void testSettingsFileString() {
		
		assertEquals("TestUsername", base.getSettingString("testusername"));
		
	}
	
	/**
	 * Reads a text from the settingsfile
	 * 
	 * Test: successful 
	 */
	@Test
	public void testSettingsFileInt() {
	
		assertEquals("10", base.getSettingString("testnumber"));
		
	}
	
	/**
	 * If the key is not in the settingsfile
	 * 
	 * Test: successful
	 */
	@Test(expected= AssertionError.class)
	public void nokey() {
		
		assertEquals("10", base.getSettingString("blabla"));
		
	}

}
