package test.content;

import static org.junit.Assert.*;
import org.junit.Test;
import content.Base;

/**
 * A test class to test the Base-Class
 * @author Helmuth Brunner
 * @version Oct 6, 2014
 * Current project: RTN
 */
public class TestBase {

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
	 * the methode will return null
	 * 
	 * Test: successful
	 */
	@Test
	public void nokeySettings() {
		
		assertEquals(null , base.getSettingString("blabla"));
		
	}
	
	/**
	 * Test the getLangString() method
	 * 
	 * Test: successful
	 */
	@Test
	public void testLangString() {
		
		assertEquals("test", base.getLangString("test"));
		
	}
	
	/**
	 * Test the getLangString() method if the key is not in the lang file
	 * the methode will return null
	 * 
	 * Test: successful
	 */
	@Test
	public void nokeyLang() {
		
		assertEquals(null, base.getLangString("balbla"));
		
	}

}
