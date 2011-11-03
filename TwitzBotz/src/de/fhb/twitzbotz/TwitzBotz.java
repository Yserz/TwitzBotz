
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.IdleThread;
import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import de.fhb.twitzbotz.helper.TwitterConnectHelper;
import java.util.logging.Level;

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
		String userToListenDefault = "TwitBot2";
		String botAccount = "@TwitBot2";
		
		try {
		
			java.util.logging.Logger.getLogger(TBController.class.getName()).setLevel(Level.OFF);
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).setLevel(Level.OFF);
			java.util.logging.Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.OFF);
			java.util.logging.Logger.getLogger(IdleThread.class.getName()).setLevel(Level.OFF);
			
			TBController tbController = new TBController();
			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();
			
			
			IdleThread thread = null;
			
			if (args.length < 1) {
				System.out.println("UserToListen: "+userToListenDefault);
				thread = new IdleThread(tbController, botAccount, propsHelper.getFunnyTexts(), userToListenDefault);
				thread.start();
			}else{
				System.out.println("UserToListen: "+ args[0]);
				thread = new IdleThread(tbController, botAccount, propsHelper.getFunnyTexts(), args[0]);
				thread.start();
			}

			
		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			java.util.logging.Logger.getLogger(TwitzBotz.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
		}
		
	}
	
}
