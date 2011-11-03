/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.TBController;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MacYser
 */
public class IdleThread extends Thread {
	private TBController tbController = null;
	private HashMap<String, String> funnyTexts = null;
	
	public IdleThread(TBController tbController, HashMap<String, String> funnyTexts){
		this.tbController = tbController;
		this.funnyTexts = funnyTexts;
	}
	@Override
	public void run() {
		String lastText = "";
		String antwort = "";
		String aktStatus = "";
		long userID = tbController.getUsersID("TwitBot2");
		
		//TODO Sleeptimer weil maximal 350 requests/stunde(~1/10s) moeglich(Anmerkung: bissi zeit lassen fuer sonstige requests)
		// 5,8 in einer minute
		do {
			System.out.println("Checking!");
			aktStatus = tbController.getUsersLatestStatus(userID).getText().replaceAll(" ", "_");
			
			if(!aktStatus.equalsIgnoreCase(lastText)){
				lastText = aktStatus;
				
				
				antwort = funnyTexts.get(aktStatus);
				System.out.println("Antwort: "+antwort);
				if(antwort != null){
					tbController.sendMessage(antwort);
				}
			}
			try {
				sleep(11000L);
			} catch (InterruptedException ex) {
				Logger.getLogger(IdleThread.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		} while (true);
    }
}
