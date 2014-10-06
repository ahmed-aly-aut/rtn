package content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * A Base-class to control the property-files.
 * @author Helmuth Brunner
 * @version Oct 6, 2014
 * Current project: RTN
 */
public class Base {

	private static Base instance;
	private String PATH= "/res/";
	private Properties settings, lang;
	
	private Logger log= Logger.getLogger(Base.class.getName());
	
	/**
	 * Private-constructor
	 */
	private Base() {
		
		settings= this.loadProperties("settings");
		this.loadSystemLanguage();
		
		lang= this.loadProperties("lang_"+this.getSettingString("lang"));
	}
	
	/**
	 * Returns the instance from the Base-Class
	 * Singleton-Pattern
	 * @return the instance from this class
	 */
	public static Base get() {
		if(instance==null)
			instance= new Base();
		return instance;
	}
	
	/**
	 * A method to convert the property-files into a Properties-Object
	 * @param file the property file
	 * @return the property from the given file
	 */
	public Properties loadProperties(String file) {
		Properties p= new Properties();

		InputStream in= this.getClass().getResourceAsStream(this.PATH+file+".properties");
		
		try {
			p.load(in);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		return p;
	}
	
	/**
	 * A method to get the value from the specific key.
	 * @param the key
	 * @return the value as String
	 */
	public String getSettingString(String key) {
		return settings.getProperty(key);
	}
	
	/**
	 * A method to get the value from the specific key
	 * @param the key
	 * @return the lang-value for the specific key
	 */
	public String getLangString(String key) {
		return lang.getProperty(key);
	}
	
	/**
	 * Sets the language to the system-language
	 */
	public void loadSystemLanguage() {
		
		String l= System.getProperty("user.language");
		
		if(l.equals("de"))
			settings.setProperty("lang", "de");
		else
			settings.setProperty("lang", "en");
		
	}
}

