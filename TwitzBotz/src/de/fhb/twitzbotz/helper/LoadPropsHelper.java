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
		describeEnviroment();
		
		props = new Properties();
		funnyTexts = new HashMap<String, String>();
		choosenLanguage = "";
		
	}
	public void loadAllProps(){
		describeEnviroment();
		
		try {
			loadProp();
			loadLang();
		} catch (IOException ex) {
			Logger.getLogger(LoadPropsHelper.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NumberFormatException ex) {
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
		describeEnviroment();
		
		FileInputStream stream = null;
		try {
			stream = new FileInputStream("src/twitzbotz.properties");
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
		describeEnviroment();
		
		System.out.println("lang: "+choosenLanguage);
		
		props.load(new FileInputStream(("src/languages/DE.properties")));
		Set<Object> keys = props.keySet();
		for (Object key : keys) {
			funnyTexts.put(key.toString(), props.get(key).toString());
		}
	}
	private void describeEnviroment() {
		StackTraceElement stackTop = new Exception().getStackTrace()[1];
		java.util.logging.Logger.getLogger(LoadPropsHelper.class.getName()).log(Level.INFO, "Logger: class = {0},\n method: {1}", new Object[]{stackTop.getClassName(), stackTop.getMethodName()});
	}
	
	public HashMap<String, String> getFunnyTexts() {
		return funnyTexts;
	}
}
