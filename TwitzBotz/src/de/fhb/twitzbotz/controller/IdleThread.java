/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.controller.TBController;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;

/**
 *
 * @author MacYser
 */
public class IdleThread extends Thread {
	private TBController tbController = null;
	private HashMap<String, String> funnyTexts = null;
	private String userToListen = "";
	private long myID = -1;
	
	public IdleThread(TBController tbController, HashMap<String, String> funnyTexts, String userToListen){
		this.tbController = tbController;
		this.funnyTexts = funnyTexts;
		this.userToListen = userToListen;
		this.myID = tbController.getMyID();
	}
	@Override
	public void run() {
		String lastText = "";
		String antwort = "";
		String aktStatusText = "";
		Status aktStatus = null;
		long listenedUserID = tbController.getUsersID(userToListen);
		
		int count = 0;
		
		do {
			count++;
			System.out.println("Checking! "+count);
			aktStatus = tbController.getUsersLatestStatus(listenedUserID);
			
			Logger.getLogger(IdleThread.class.getName()).log(Level.INFO, "aktStatusReplyID = {0}, \nMyID = {1}", new Object[]{aktStatus.getInReplyToUserId(), myID});
			if (aktStatus.getInReplyToUserId()==myID) {
				aktStatusText = aktStatus.getText().replaceAll(" ", "_").toLowerCase().replaceAll("@twitbot2_", "");
				
				
				//Sperre, dass er nicht immer wieder das selbe posted, obwohl nichts neues geposted wurde
				if(!aktStatusText.equalsIgnoreCase(lastText)){
					lastText = aktStatusText;


					antwort = funnyTexts.get(aktStatusText);
					
					if(antwort != null){
						if(!antwort.equals("1970")){
							
							System.out.println("Antwort: "+"@"+userToListen+" "+antwort);
							tbController.sendMessage("@"+userToListen+" "+antwort);
						}else{
							//the milliseconds since January 1, 1970, 00:00:00 GMT.
							tbController.sendMessage("@"+userToListen+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT.)");
						}
						
					}
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
	private long systemTime(){
		return new Date().getTime();
	}
}
