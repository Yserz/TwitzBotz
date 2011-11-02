
package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectHelper;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
 * TODO Dieser Controller muss gesplittet werden -> Verbindungscontroller/helper
 *											-> Programmablaufcontroller
 */
public class TBController {

	private Twitter twitterService = null;
	private static final Logger rootLogger = Logger.getRootLogger();
	private static final Logger logger = Logger.getLogger(TBController.class);
	private Status lastStatus = null;

	/**
	 * Default-Konstruktor
	 * 
	 * nebst initialisierung des Objektes wird das Verbinden zum Service angestoßen.
	 */
	public TBController() {
		rootLogger.setLevel(Level.INFO);
		logger.setLevel(Level.INFO);



		//Verbindungsaufbau mit hardcodet Account(keine PIN-Eingabe)
		twitterService = new TwitterConnectHelper().connectToServiceSingleAcc();

	}

	public void testController() {

		System.out.println("Showing your timeline.\n");
		for (Status status : getMyTimeline()) {
			System.out.println(status.getId() + " <" + status.getUser().getName() + "> : " + status.getText());
		}


		try {
			System.out.println("Status: " + twitterService.showStatus(new Long(131785411528896514L)).getUser());
		} catch (TwitterException ex) {
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		sendMessage("Hallo ich teste das hier mal.");
	}

	/**
	 * Methode zum Auslesen aller aktuellen Timeline auf dem jeweiligen Twitteraccount.
	 * TODO uebergabeparameter mit anzahl auszulesender tweets
	 * @return TweetList
	 */
	public List<Status> getMyTimeline() {
		System.out.println("Logger: class = " + this.getClass().getSimpleName()
						   + ",\n method: " + this.getClass().getEnclosingMethod());

		List<Status> statuses = null;
		try {

			statuses = twitterService.getHomeTimeline();

		} catch (TwitterException ex) {
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalStateException ex) {
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}


		return statuses;
	}

	public void sendMessage(String message) {
		try {
			lastStatus = twitterService.updateStatus(message);
			System.out.println("Updated Status successfully to " + lastStatus.getText());
		} catch (TwitterException ex) {
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
}
