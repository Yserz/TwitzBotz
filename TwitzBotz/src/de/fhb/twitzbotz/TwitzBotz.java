
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.IdleThread;
import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import de.fhb.twitzbotz.helper.TwitterConnectHelper;
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
		
		try {
			
			FileHandler fh = new FileHandler("log/log_"+new Date()+".xml");
		
			Logger.getLogger(TBController.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TBController.class.getName()).addHandler(fh);
			
			Logger.getLogger(TwitterConnectHelper.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(TwitterConnectHelper.class.getName()).addHandler(fh);
			
			Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(LoadPropsHelper.class.getName()).addHandler(fh);
			
			Logger.getLogger(IdleThread.class.getName()).setLevel(Level.SEVERE);
			Logger.getLogger(IdleThread.class.getName()).addHandler(fh);
			
			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();
			
			TBController tbController = new TBController(propsHelper.getFunnyTexts());
			
			
			
			
			
			IdleThread thread = null;
			
			if (args.length < 1) {
				System.out.println("UserToListen: @TwitBot2");
				tbController.startUserStream();
			}else{
				System.out.println("UserToListen: "+ args[0]);
				tbController.startUserStream(args[0]);
			}

			
		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			java.util.logging.Logger.getLogger(TwitzBotz.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
		}
		
	}
	
}
