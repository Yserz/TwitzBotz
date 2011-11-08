package de.fhb.twitzbotz.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Der LoadPropsHelper ist zuständig für das Laden von Propertie/Text-Datein.
 *
 * @author Michael Koppen
 */
public class LoadPropsHelper {
	private final static Logger LOGGER = Logger.getLogger(LoadPropsHelper.class.getName());
	
	private Properties props;
	private HashMap<String, String> funnyTexts;
	private ArrayList<String> userToListen;
	private String choosenLanguage;

	public LoadPropsHelper() {
		LOGGER.setLevel(Level.SEVERE);

		props = new Properties();
		funnyTexts = new HashMap<String, String>();
		userToListen = new ArrayList<String>();
		choosenLanguage = "";

	}

	public void loadAllProps() {


		try {
			loadProp();
			loadLang();
			loadUserToListen();
		} catch (FileNotFoundException fnf){
			System.err.println("File wurde nicht gefunden.");
			Logger.getLogger(LoadPropsHelper.class.getName()).log(Level.SEVERE, null, fnf);
		} catch (IOException ex) {
			System.err.println("Unknown input-output-error occured.");
			Logger.getLogger(LoadPropsHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 

	}

	/**
	 * loading the Properties
	 * @throws IOException Input output failure
	 * @throws ClassCastException failed to cast a class
	 * @throws NumberFormatException failed to format string into integer
	 */
	private void loadProp() throws IOException {


		FileInputStream stream = null;
		try {
			stream = new FileInputStream("twitzbotz.properties");
			props.load(stream);
		} finally {
			choosenLanguage = props.getProperty("LANG", "DE");

		}
	}

	/**
	 * Method to load the property language file
	 * @throws FileNotFoundException file was not found
	 * @throws IOException input output failure
	 */
	private void loadLang() throws IOException {


		props.load(new FileInputStream("languages/DE.properties"));
		Set<Object> keys = props.keySet();
		for (Object key : keys) {
			funnyTexts.put(key.toString(), props.get(key).toString());
		}
	}

	private void loadUserToListen() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("user.txt"));

		String zeile = br.readLine();
		while (zeile != null) {
			userToListen.add(zeile);
			zeile = br.readLine();
		}
	}

	public HashMap<String, String> getFunnyTexts() {
		return funnyTexts;
	}
	
	public ArrayList<String> getUserToListen() {
		return userToListen;
	}
}
