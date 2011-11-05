
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.ServiceController;
import de.fhb.twitzbotz.controller.StreamController;
import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import de.fhb.twitzbotz.helper.TwitterConnectHelperBase;
import de.fhb.twitzbotz.helper.TwitterConnectServiceHelper;
import de.fhb.twitzbotz.helper.TwitterConnectStreamHelper;
import java.util.Date;
import java.util.logging.FileHandler;
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
			
			FileHandler fh = new FileHandler("log/log_"+new Date()+".xml");
		
			Logger.getLogger(TBController.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TBController.class.getName()).addHandler(fh);
			
			Logger.getLogger(StreamController.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(StreamController.class.getName()).addHandler(fh);
			
			Logger.getLogger(ServiceController.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(ServiceController.class.getName()).addHandler(fh);
			
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).addHandler(fh);
			
			Logger.getLogger(TwitterConnectStreamHelper.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TwitterConnectStreamHelper.class.getName()).addHandler(fh);
			
			Logger.getLogger(TwitterConnectServiceHelper.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TwitterConnectServiceHelper.class.getName()).addHandler(fh);
			
			Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(LoadPropsHelper.class.getName()).addHandler(fh);
			
			
			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();
			
			TBController tbController = new TBController(propsHelper.getFunnyTexts());

			
		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			java.util.logging.Logger.getLogger(TwitzBotz.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
		}
	}
	
}
