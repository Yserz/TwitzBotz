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
	private String userToListen = "";
	
	public IdleThread(TBController tbController, HashMap<String, String> funnyTexts, String userToListen){
		this.tbController = tbController;
		this.funnyTexts = funnyTexts;
		this.userToListen = userToListen;
	}
	@Override
	public void run() {
		String lastText = "";
		String antwort = "";
		String aktStatus = "";
		long userID = tbController.getUsersID(userToListen);
		int count = 0;
		
		do {
			count++;
			System.out.println("Checking! "+count);
			aktStatus = tbController.getUsersLatestStatus(userID).getText().replaceAll(" ", "_");
			
			//Sperre, dass er nicht immer wieder das selbe posted, obwohl nichts neues geposted wurde
			if(!aktStatus.equalsIgnoreCase(lastText)){
				lastText = aktStatus;
				
				
				antwort = funnyTexts.get(aktStatus);
				System.out.println("Antwort: "+"@"+userToListen+" "+antwort);
				if(antwort != null){
					tbController.sendMessage("@"+userToListen+" "+antwort);
				}
			}
			try {
				//327 Requests/Stunde -> max. 350 Requests/Stunde
				sleep(11000L);
			} catch (InterruptedException ex) {
				Logger.getLogger(IdleThread.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		} while (true);
    }
}
