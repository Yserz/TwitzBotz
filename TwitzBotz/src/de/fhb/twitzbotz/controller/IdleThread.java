/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.twitzbotz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;

/**
 * Thread zum abfragen ob neue tweets geposted wurden.
 * *Deprecated*
 * 
 * @author Michael Koppen
 */
public class IdleThread extends Thread {
	private TBController tbController = null;
	private HashMap<String, String> funnyTexts = null;
	private String userToListen = "";
	private long myID = -1;
	private long shortSleeptime = 11000;
	private long longSleeptime = 30000L;
	private String botAccount = "";
	private int sleeptimerCount = 0;
	
	public IdleThread(TBController tbController,String botAccount, HashMap<String, String> funnyTexts, String userToListen){
		
		this.tbController = tbController;
		this.funnyTexts = funnyTexts;
		this.userToListen = userToListen;
		this.myID = tbController.getMyID();
		this.botAccount = botAccount.toLowerCase();
	}
	@Override
	public void run() {
		
		int globalCount = 0;
		String lastStatus = "";
		sleeptimerCount = 0;
		
		Status aktStatus = null;
		long listenedUserID = tbController.getUsersID(userToListen);
		
		
		
		do {
			sleeptimerCount++;
			globalCount++;
			System.out.println("\u001b[2J");
			System.out.println("\n\nChecking! \nGlobalCount: "+globalCount+", \nSleeptimerCount: "+sleeptimerCount);
			
			aktStatus = tbController.getUsersLatestStatus(listenedUserID);
			lastStatus = checkIfUserSpeaksWithMe(aktStatus, lastStatus);
			
			if (sleeptimerCount>10) {
				threadSleep(longSleeptime);
			}else{
				threadSleep(shortSleeptime);
			}
			
			
			
		} while (true);
    }
	private long systemTime(){
		
		return new Date().getTime();
	}
	private void threadSleep(long sleeptime){
		
		try {
			//327 Requests/Stunde -> max. 350 Requests/Stunde
			sleep(sleeptime);
		} catch (InterruptedException ex) {
			System.err.println("Unknown thread-error occured.");
			Logger.getLogger(IdleThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void sendAnswerToListenedUser(String aktStatusText) {
		
		String antwort = "";
		antwort = funnyTexts.get(aktStatusText);
					
		if(antwort != null){
			sleeptimerCount = 0;
			if(!antwort.equals("1970")){

				System.out.println("Antwort: "+"@"+userToListen+" "+antwort);
				tbController.sendMessage("@"+userToListen+" "+antwort);
			}else{
				//the milliseconds since January 1, 1970, 00:00:00 GMT.
				tbController.sendMessage("@"+userToListen+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT.)");
			}

		}else{
			System.out.println("no elequent response there :/");
		}
	}
	/**
	 * TODO sprechender name
	 * 
	 * @param aktStatus written by userToListen
	 * @param lastStatus written by userToListen
	 * @return lastStatus written by userToListen(may changed)
	 */
	private String checkIfUserSpeaksWithMe(Status aktStatus, String lastStatus) {
		
		String aktStatusText = "";
		
		Logger.getLogger(IdleThread.class.getName()).log(Level.INFO, "aktStatusReplyID = {0}, \nMyID = {1}", new Object[]{aktStatus.getInReplyToUserId(), myID});
		if (aktStatus.getInReplyToUserId()==myID) {
			aktStatusText = aktStatus.getText().replaceAll(" ", "_").toLowerCase();
			aktStatusText = aktStatusText.replaceAll(botAccount+"_", "");

			System.out.println("AktStatusText:     "+aktStatusText);
			//Sperre, dass er nicht immer wieder das selbe posted, obwohl nichts neues geposted wurde
			if(!aktStatusText.equalsIgnoreCase(lastStatus)){
				lastStatus = aktStatusText;

				sendAnswerToListenedUser(aktStatusText);
			}else{
				System.out.println("No new shit out there :/");
			}

		}else{
			System.out.println("Noone's speaking with me :/");
		}
		return lastStatus;
	}
}
