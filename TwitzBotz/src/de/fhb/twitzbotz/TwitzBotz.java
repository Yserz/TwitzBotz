
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.TBController;

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
		System.out.println("hello world!");
		
		TBController tbController = new TBController();
		
		tbController.testController();
	}
}
