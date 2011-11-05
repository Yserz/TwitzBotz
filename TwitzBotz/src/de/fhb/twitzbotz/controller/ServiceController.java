package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectServiceHelper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author Michael Koppen
 */
public class ServiceController{
	private Twitter twitterService = null;
	private TwitterConnectServiceHelper twitterConnectServiceHelper = null;
	
	private HashMap<String, String> funnyTexts = null;
	
	public ServiceController(HashMap<String, String> funnyTexts){
		
		twitterConnectServiceHelper = new TwitterConnectServiceHelper();
		twitterService = twitterConnectServiceHelper.connectToService();
		
		this.funnyTexts = funnyTexts;
	}
	
	public void sendAnswerToListenedUser(Status status) {
		String aktStatusText = "";
		String toUser = status.getUser().getScreenName();
		
		aktStatusText = status.getText().replaceAll(" ", "_").toLowerCase();
		aktStatusText = aktStatusText.replaceAll("@"+getMyScreenName().toLowerCase()+"_", "");
		

		System.out.println("AktStatusTextAfterFormat: "+aktStatusText);
		
		
		String antwort = "";
		antwort = funnyTexts.get(aktStatusText);
					
		if(antwort != null){
			if(!antwort.equals("1970")){

				System.out.println("Antwort: "+"@"+toUser+" "+antwort);
				sendMessage("@"+toUser+" "+antwort);
			}else{
				//the milliseconds since January 1, 1970, 00:00:00 GMT.
				sendMessage("@"+toUser+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT.)");
			}

		}else{
			System.out.println("no elequent response there :/");
		}
	}
	
	/**
	 * Methode zum Auslesen aller aktuellen Timeline auf dem jeweiligen Twitteraccount.
	 * TODO uebergabeparameter mit anzahl auszulesender tweets
	 * @return TweetList
	 */
	public List<Status> getMyTimeline() {
		

		List<Status> statuses = null;
		try {

			statuses = twitterService.getHomeTimeline();

		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}


		return statuses;
	}

	public List<Status> getUsersTimeline(String user) {
		
		
		return getUsersTimeline(this.getUsersID(user));
	}

	public List<Status> getUsersTimeline(long userID) {
		
		
		List<Status> userTimeline = null;
		try {
			userTimeline = twitterService.getUserTimeline(userID);
			java.util.logging.Logger.getLogger(ServiceController.class.getName()).log(java.util.logging.Level.INFO, "User: {0}", userTimeline);

		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userTimeline;
	}
	
	public Status getUsersLatestStatus(String user) {
		
		
		return getUsersLatestStatus(this.getUsersID(user));
	}

	public Status getUsersLatestStatus(long userID) {
		
		
		Status userStatus = null;
		try {
			userStatus = twitterService.showUser(userID).getStatus();
			java.util.logging.Logger.getLogger(ServiceController.class.getName()).log(java.util.logging.Level.INFO, "latestStatus: {0}, \nlatestStatusRaw: {1}", new Object[]{userStatus.getText(), userStatus});

		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userStatus;
	}

	public void sendMessage(String message) {
		
		
		Status lastStatus = null;
		try {
			if (message.length()>140) {
				System.out.println("Message("+message+") is to long. Maximum is 140(your messagelength: "+message.length()+") signs. ");
				throw new TwitterException("Message("+message+") is to long. Maximum is 140(your messagelength: "+message.length()+") signs.");
			}
			lastStatus = twitterService.updateStatus(message);
			System.out.println("Updated Status successfully to " + lastStatus.getText());
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
	}

	public long getMyID() {
		
		
		long userID = -1;
		try {
			userID = twitterService.getId();
			java.util.logging.Logger.getLogger(ServiceController.class.getName()).log(java.util.logging.Level.INFO, "MyID: {0}", userID);
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userID;
	}
	
	public String getMyScreenName() {
		
		try {
			return twitterService.getScreenName();
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return null;
	}
	
	public long getUsersID(String user) {
		
		
		long userID = -1;
		try {
			userID = twitterService.showUser(user).getId();
			java.util.logging.Logger.getLogger(ServiceController.class.getName()).log(java.util.logging.Level.INFO, "ID: {0}", userID);
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userID;
	}
	
	private long systemTime(){
		
		return new Date().getTime();
	}
}
