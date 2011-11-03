
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.IdleThread;
import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import de.fhb.twitzbotz.helper.TwitterConnectHelper;
import java.util.logging.Level;

/**
 *
 * @author Michael Koppen
 */
/*
 * TODO Create Thread to idle the messagesearch
 */
public class TwitzBotz {

	/**
	 * Rambow  - zeruby
	 * Self    - TwitBot2
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
		
			java.util.logging.Logger.getLogger(TBController.class.getName()).setLevel(Level.INFO);
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).setLevel(Level.SEVERE);
			java.util.logging.Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.SEVERE);

			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();


			TBController tbController = new TBController();
			
			
			IdleThread thread = null;
			
			
			
			if (args.length < 1) {
				System.out.println("UserToListen: TwitBot2");
				thread = new IdleThread(tbController,propsHelper.getFunnyTexts(), "zeruby");
				thread.start();
			}else{
				System.out.println("UserToListen: "+ args[0]);
				thread = new IdleThread(tbController,propsHelper.getFunnyTexts(), args[0]);
				thread.start();
			}

			
			
			
		} catch (Exception e) {
			java.util.logging.Logger.getLogger(TwitzBotz.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
		}
		
	}
	
}
