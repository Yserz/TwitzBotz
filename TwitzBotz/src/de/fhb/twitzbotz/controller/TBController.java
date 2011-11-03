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
	private Status lastStatus = null;
	

	/**
	 * Default-Konstruktor
	 * 
	 * nebst initialisierung des Objektes wird das Verbinden zum Service angestoßen.
	 */
	public TBController() {
		describeEnviroment();

		
		//Verbindungsaufbau mit hardcodet Account(keine PIN-Eingabe)
		twitterService = new TwitterConnectHelper().connectToServiceSingleAcc();

	}

	/**
	 * Methode zum Auslesen aller aktuellen Timeline auf dem jeweiligen Twitteraccount.
	 * TODO uebergabeparameter mit anzahl auszulesender tweets
	 * @return TweetList
	 */
	public List<Status> getMyTimeline() {
		describeEnviroment();

		List<Status> statuses = null;
		try {

			statuses = twitterService.getHomeTimeline();

		} catch (TwitterException ex) {
			handleTwitterException(ex);
		} catch (IllegalStateException ex) {
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}


		return statuses;
	}

	public Status getUsersLatestStatus(String user) {
		describeEnviroment();
		return getUsersLatestStatus(this.getUsersID(user));
	}

	public Status getUsersLatestStatus(long userID) {
		describeEnviroment();
		Status userStatus = null;
		try {
			userStatus = twitterService.showUser(userID).getStatus();
			System.out.println("latestStatus: " + userStatus.getText());

		} catch (TwitterException ex) {
			handleTwitterException(ex);
		}
		return userStatus;
	}

	public List<Status> getUsersTimeline(String user) {
		describeEnviroment();
		return getUsersTimeline(this.getUsersID(user));
	}

	public List<Status> getUsersTimeline(long userID) {
		describeEnviroment();
		List<Status> userTimeline = null;
		try {
			userTimeline = twitterService.getUserTimeline(userID);
			System.out.println("User: " + userTimeline);

		} catch (TwitterException ex) {
			handleTwitterException(ex);
		}
		return userTimeline;
	}

	public void sendMessage(String message) {
		describeEnviroment();
		try {
			lastStatus = twitterService.updateStatus(message);
			System.out.println("Updated Status successfully to " + lastStatus.getText());
		} catch (TwitterException ex) {
			handleTwitterException(ex);
		}
	}

	public long getUsersID(String user) {
		describeEnviroment();
		long userID = -1;
		try {
			userID = twitterService.showUser(user).getId();
			System.out.println("ID: " + userID);
		} catch (TwitterException ex) {
			handleTwitterException(ex);
		}
		return userID;
	}

	private void describeEnviroment() {
		StackTraceElement stackTop = new Exception().getStackTrace()[1];
		java.util.logging.Logger.getLogger(TBController.class.getName()).log(Level.INFO, "Logger: class = {0},\n method: {1}", new Object[]{stackTop.getClassName(), stackTop.getMethodName()});
	}
	private void handleTwitterException(TwitterException ex){
		describeEnviroment();
		if (400 == ex.getStatusCode()) {
				System.err.println("Rate limit exceeded. Clients may not make more than "+ex.getRateLimitStatus().getHourlyLimit()+" requests per hour. \nThe next reset is "+ex.getRateLimitStatus().getResetTime());
				java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} else if(-1 == ex.getStatusCode()){
				System.err.println("Can not connect to the internet or the host is down.");
				java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} else{
				java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			}
	}
}
