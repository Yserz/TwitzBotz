package de.fhb.twitzbotz.controller;

import java.util.ArrayList;
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
	private final static Logger LOGGER = Logger.getLogger(TBController.class.getName());
	
	private ServiceController serviceController = null;
	private StreamController streamController = null;
	
	private ArrayList<String> userToListen;
	private long myID;
	private HashMap<String, String> funnyTexts = null;

	public TBController(ArrayList<String> userToListen, HashMap<String, String> funnyTexts) {
		LOGGER.setLevel(Level.SEVERE);
		
		this.userToListen = userToListen;
		this.funnyTexts = funnyTexts;
		
		serviceController = new ServiceController(funnyTexts);
		this.myID = serviceController.getMyID();
		streamController = new StreamController(this);
		
		
		
		startStream();
		
	}
	private void startStream(){
		int count = 0;
		long [] userToListenID = new long[userToListen.size()];
		
		if (userToListen.size()>6) {
			System.err.println("WARNING: To many users to listen. \n"
					+ "You have more than 6 users you wanna listen to, \n"
					+ "this could be a reason for really bad errors.\n"
					+ "Please dont hurt me :/");
		}
		for (int i = 0; i < userToListen.size(); i++) {
			System.out.println("UserToListen: "+userToListen.get(i));
			userToListenID[i] = serviceController.getUsersID(userToListen.get(i));
			count++;
		}
		System.out.println("User To Listen Anzahl: "+count);
		streamController.startUserStream(userToListenID);
	}

	public boolean isUserSpeakingWithMe(Status aktStatus) {
		
		Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "aktStatusReplyID = {0}, \nMyID = {1}", 
																			 new Object[]{aktStatus.getInReplyToUserId(), 
																						  myID});
		if (aktStatus.getInReplyToUserId()==myID) {
			return true;

		}else{
			System.out.println("Aktueller Status: ("+aktStatus.getUser().getScreenName()+") \n"+aktStatus.getText());
			System.out.println("Noone's speaking with me :/");
		}
		return false;
	}
	public void sendAnswerToListenedUser(Status status) {
		String aktStatusText = "";
		String toUser = status.getUser().getScreenName();
		String antwort = "";
		
		System.out.println("Aktueller Status: ("+toUser+") \n"+status.getText());
		
		aktStatusText = status.getText().replaceAll(" ", "_").toLowerCase();
		aktStatusText = aktStatusText.replaceAll("@"+serviceController.getMyScreenName().toLowerCase()+"_", "");
		

		Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "AktStatusTextAfterFormat: {0}", 
																			 aktStatusText);
		
		
		antwort = funnyTexts.get(aktStatusText);
					
		if(antwort != null){
			if(!antwort.equals("1970")){
				serviceController.sendMessage("@"+toUser+" "+antwort);
			}else{
				serviceController.sendMessage("@"+toUser+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT. And thats a long in java ;D)");
			}

		}else{
			
			System.out.println("No answer there :/");
					
		}
	}
	private long systemTime(){
		
		return new Date().getTime();
	}
}
