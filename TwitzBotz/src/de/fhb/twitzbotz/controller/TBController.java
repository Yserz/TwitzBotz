package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectHelper;
import java.util.List;
import java.util.logging.Level;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.Twitter;

/**
 *
 * 
 * @author Michael Koppen
 */
/*
 * TODO Die Keys sind so nicht sicher und muessten verschlüsselt gespeichert werden.
 */
public class TBController {

	private Twitter twitterService = null;
	private TwitterConnectHelper twitterConnectHelper;
	

	/**
	 * Default-Konstruktor
	 * 
	 * nebst initialisierung des Objektes wird das Verbinden zum Service angestoßen.
	 */
	public TBController() {
		
		
		twitterConnectHelper = new TwitterConnectHelper();
		//Verbindungsaufbau mit hardcodet Account(keine PIN-Eingabe)
		twitterService = twitterConnectHelper.connectToServiceSingleAcc();

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
			twitterConnectHelper.handleTwitterException(ex);
		}


		return statuses;
	}

	public Status getUsersLatestStatus(String user) {
		
		
		return getUsersLatestStatus(this.getUsersID(user));
	}

	public Status getUsersLatestStatus(long userID) {
		
		
		Status userStatus = null;
		try {
			userStatus = twitterService.showUser(userID).getStatus();
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.INFO, "latestStatus: {0}, \nlatestStatusRaw: {1}", new Object[]{userStatus.getText(), userStatus});

		} catch (TwitterException ex) {
			twitterConnectHelper.handleTwitterException(ex);
		}
		return userStatus;
	}

	public List<Status> getUsersTimeline(String user) {
		
		
		return getUsersTimeline(this.getUsersID(user));
	}

	public List<Status> getUsersTimeline(long userID) {
		
		
		List<Status> userTimeline = null;
		try {
			userTimeline = twitterService.getUserTimeline(userID);
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.INFO, "User: {0}", userTimeline);

		} catch (TwitterException ex) {
			twitterConnectHelper.handleTwitterException(ex);
		}
		return userTimeline;
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
			twitterConnectHelper.handleTwitterException(ex);
		}
	}

	public long getUsersID(String user) {
		
		
		long userID = -1;
		try {
			userID = twitterService.showUser(user).getId();
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.INFO, "ID: {0}", userID);
		} catch (TwitterException ex) {
			twitterConnectHelper.handleTwitterException(ex);
		}
		return userID;
	}
	public long getMyID() {
		
		
		long userID = -1;
		try {
			userID = twitterService.getId();
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.INFO, "MyID: {0}", userID);
		} catch (TwitterException ex) {
			twitterConnectHelper.handleTwitterException(ex);
		}
		return userID;
	}

	
}
