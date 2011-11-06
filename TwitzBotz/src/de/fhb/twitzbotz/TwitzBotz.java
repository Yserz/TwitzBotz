
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.ServiceController;
import de.fhb.twitzbotz.controller.StreamController;
import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import de.fhb.twitzbotz.helper.TwitterConnectHelperBase;
import de.fhb.twitzbotz.helper.TwitterConnectServiceHelper;
import de.fhb.twitzbotz.helper.TwitterConnectStreamHelper;
import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *	TwitzBotz
 * 
 * TwitzBotz ist ein automatischer Twitter-Client.
 * Sie können TwitzBotz Twitter-User beobachten lassen. 
 * Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), 
 * wird TwitzBotz antworten.
 * 
 * @author Michael Koppen
 */
public class TwitzBotz {

	/**
	 * TwitzBotz ist ein automatischer Twitter-Client.
	 * Sie können TwitzBotz Twitter-User beobachten lassen. 
	 * Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), 
	 * wird TwitzBotz antworten.
	 * 
	 * TODO genauere beschreibung
	 * 
	 * @param args first param should be the user to listen to.
	 */
	public static void main(String[] args) {
		TwitzBotz app = new TwitzBotz();
		app.init(args);
		
		
	}
	public void init(String[] args){
		try {
			initLoggers(initRootLogger());
			
			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();
			
			TBController tbController = new TBController(propsHelper.getFunnyTexts());

			
		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	private void initLoggers(Logger rootLogger){
				
		rootLogger.setLevel(Level.SEVERE);

		//TODO create attributelogger in every class
		Logger.getLogger(TwitzBotz.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TBController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(StreamController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(ServiceController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectHelperBase.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectStreamHelper.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectServiceHelper.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.SEVERE);
			
			
	}
	private Logger initRootLogger(){
		Level consoleHandlerLevel	= Level.OFF;
		Level fileHandlerLevel		= Level.SEVERE;
		
		//setting up ConsoleHandler
		Logger rootLogger = Logger.getLogger("");

		Handler [] handlers = rootLogger.getHandlers();

		ConsoleHandler chandler = null;

		for (int i = 0; i < handlers.length; i++) {
			if (handlers[i] instanceof ConsoleHandler) {
				chandler = (ConsoleHandler)handlers[i];
			}
		}

		if (chandler != null) {
			chandler.setLevel(consoleHandlerLevel);
		}else{
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, "No ConsoleHandler there.");
		}
		
		//setting up FileHandler
		FileHandler fh = null;
		try {
			fh = new FileHandler("log/log_"+new Date()+".xml");
			fh.setLevel(fileHandlerLevel);
		} catch (IOException ex) {
			System.err.println("Cannot find the Log-Folder so I will not log anything.");
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			System.err.println("Cannot open/access Log-Folder so I will not log anything.");
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (fh != null) {
			rootLogger.addHandler(fh);
		}
		return rootLogger;
	}
	
}
