/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.twitzbotz.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MacYser
 */
public class LoadPropsHelper {
	private Properties props;
	private HashMap<String, String> funnyTexts;
	private String choosenLanguage;
	
	
	

	public LoadPropsHelper() {
		
		
		props = new Properties();
		funnyTexts = new HashMap<String, String>();
		choosenLanguage = "";
		
	}
	public void loadAllProps(){
		
		
		try {
			loadProp();
			loadLang();
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
	private void loadProp() throws IOException{
		
		
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
	private void loadLang() throws FileNotFoundException, IOException{
		
		
		props.load(new FileInputStream("languages/DE.properties"));
		Set<Object> keys = props.keySet();
		for (Object key : keys) {
			funnyTexts.put(key.toString(), props.get(key).toString());
		}
	}
	
	public HashMap<String, String> getFunnyTexts() {
		return funnyTexts;
	}
}
