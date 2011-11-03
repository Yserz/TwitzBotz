
package de.fhb.twitzbotz;

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
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
		
			java.util.logging.Logger.getLogger(TBController.class.getName()).setLevel(Level.OFF);
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).setLevel(Level.OFF);
			java.util.logging.Logger.getLogger(LoadPropsHelper.class.getName()).setLevel(Level.ALL);

			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();


			TBController tbController = new TBController();

			IdleThread thread = new IdleThread(tbController,propsHelper.getFunnyTexts());
			
			thread.start();
		} catch (Exception e) {
			java.util.logging.Logger.getLogger(TwitzBotz.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
		}
		
	}
	
}
