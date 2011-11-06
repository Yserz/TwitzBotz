
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
import sun.tools.javap.JavapPrinter;

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
			initLoggers();
			
			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();
			
			TBController tbController = new TBController(propsHelper.getFunnyTexts());

			
		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	private void initLoggers() throws IOException{
		FileHandler fh = new FileHandler("log/log_"+new Date()+".xml");

		Logger rootLogger = Logger.getLogger("");

		Handler [] handlers = rootLogger.getHandlers();

		ConsoleHandler chandler = null;

		for (int i = 0; i < handlers.length; i++) {
			if (handlers[i] instanceof ConsoleHandler) {
				chandler = (ConsoleHandler)handlers[i];
			}
		}

		if (chandler != null) {
			chandler.setLevel(Level.OFF);
		}
		
		rootLogger.addHandler(fh);
		rootLogger.setLevel(Level.SEVERE);

		Logger.getLogger(TwitzBotz.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TBController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(StreamController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(ServiceController.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectHelperBase.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectStreamHelper.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(TwitterConnectServiceHelper.class.getName()).setLevel(Level.SEVERE);
		Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.SEVERE);
			
			
	}
}
