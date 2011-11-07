package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectServiceHelper;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Der ServiceController verwaltet alle Funktionen um den normalen Twitter-Service.
 * z.B.: 
 * - Senden von Stati,
 * - Einzelne Anfragen von Stati(kein Stream)
 * - Anfragen von ganzen Timelines
 *
 * @author Michael Koppen
 */
public class ServiceController{
	private Twitter twitterService = null;
	private TwitterConnectServiceHelper twitterConnectServiceHelper = null;
	
	
	public ServiceController(HashMap<String, String> funnyTexts){
		
		twitterConnectServiceHelper = new TwitterConnectServiceHelper();
		twitterService = twitterConnectServiceHelper.connectToService();
		
	}
	
	/**
	 * Methode zum Auslesen der aktuellen Timeline auf dem Bot-Twitteraccount.
	 * TODO uebergabeparameter mit anzahl auszulesender tweets
	 * 
	 * @return Liste von Stati
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

	/**
	 * Methode zum Auslesen der aktuellen Timeline auf dem jeweiligen Twitteraccount.
	 * TODO uebergabeparameter mit anzahl auszulesender tweets
	 * 
	 * @param user von dem die Timeline gelesen werden soll
	 * @return Liste von Stati
	 */
	public List<Status> getUsersTimeline(String user) {
		
		
		return getUsersTimeline(this.getUsersID(user));
	}

	public List<Status> getUsersTimeline(long userID) {
		
		
		List<Status> userTimeline = null;
		try {
			userTimeline = twitterService.getUserTimeline(userID);

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
			Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, "latestStatus: {0}, \nlatestStatusRaw: {1}", 
																				  new Object[]{userStatus.getText(), 
																							   userStatus});

		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userStatus;
	}

	public void sendMessage(String message) {
		
		try {
			if (message.length()>140) {
				System.out.println("Message("+message+") is to long. Maximum is "
								   + "140(your messagelength: "+message.length()+") signs. ");
				throw new TwitterException("Message("+message+") is to long. "
										   + "Maximum is 140(your messagelength: "+message.length()+") signs.");
			}
			System.out.println("Antwort: "+message);
			twitterService.updateStatus(message);
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
	}

	public long getMyID() {
		
		
		long userID = -1;
		try {
			userID = twitterService.getId();
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
		} catch (TwitterException ex) {
			twitterConnectServiceHelper.handleTwitterException(ex);
		}
		return userID;
	}
	
}
