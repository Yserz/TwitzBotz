package de.fhb.twitzbotz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;


/**
 * Der TBController ist zuständig für die tiefere Logik des Programms.
 * 
 * @author Michael Koppen
 */
public class TBController {
	private ServiceController serviceController = null;
	private StreamController streamController = null;
	
	//TODO Make a List
	private String userToListen = "TwitBot2";
	private long myID;
	private HashMap<String, String> funnyTexts = null;

	public TBController(HashMap<String, String> funnyTexts) {
		this.funnyTexts = funnyTexts;
		
		serviceController = new ServiceController(funnyTexts);
		this.myID = serviceController.getMyID();
		streamController = new StreamController(this);
		
		
		
		startStreams();
		
	}
	private void startStreams(){
		//TODO Start a for-loop over the userlist(Anmerkung: nicht mehr als 6 Streams)
		streamController.startUserStream(serviceController.getUsersID(userToListen));
	}

	/**
	 * TODO Doku
	 * 
	 * @param aktStatus written by userToListen
	 * @param lastStatus written by userToListen
	 * @return lastStatus written by userToListen(may changed)
	 */
	public boolean isUserSpeakingWithMe(Status aktStatus) {
		
		Logger.getLogger(StreamController.class.getName()).log(Level.INFO, "aktStatusReplyID = {0}, \nMyID = {1}", new Object[]{aktStatus.getInReplyToUserId(), myID});
		if (aktStatus.getInReplyToUserId()==myID) {
			return true;

		}else{
			System.out.println("Noone's speaking with me :/");
		}
		return false;
	}
	public void sendAnswerToListenedUser(Status status) {
		String aktStatusText = "";
		String toUser = status.getUser().getScreenName();
		
		aktStatusText = status.getText().replaceAll(" ", "_").toLowerCase();
		aktStatusText = aktStatusText.replaceAll("@"+serviceController.getMyScreenName().toLowerCase()+"_", "");
		

		System.out.println("AktStatusTextAfterFormat: "+aktStatusText);
		
		
		String antwort = "";
		antwort = funnyTexts.get(aktStatusText);
					
		if(antwort != null){
			if(!antwort.equals("1970")){

				System.out.println("Antwort: "+"@"+toUser+" "+antwort);
				serviceController.sendMessage("@"+toUser+" "+antwort);
			}else{
				//the milliseconds since January 1, 1970, 00:00:00 GMT.
				serviceController.sendMessage("@"+toUser+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT.)");
			}

		}else{
			System.out.println("no elequent response there :/");
		}
	}
	private long systemTime(){
		
		return new Date().getTime();
	}
}
