package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectHelper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterException;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.UserStreamListener;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;

/**
 * TODO Beschreibung TBController
 * 
 * @author Michael Koppen
 */
/*
 * TODO Die Keys sind so nicht sicher und muessten verschlüsselt gespeichert werden.
 */
public class TBController {

	private Twitter twitterService = null;
	private TwitterStream twitterStream = null;
	private TwitterConnectHelper twitterConnectHelper;
	private HashMap<String, String> funnyTexts = null;
	
	private String userToListen = "";
	private final long myID;
	private String botAccount = "TwitBot2";

	/**
	 * Default-Konstruktor
	 * 
	 * nebst initialisierung des Objektes wird das Verbinden zum Service angestoßen.
	 */
	public TBController(HashMap<String, String> funnyTexts) {
		
		
		twitterConnectHelper = new TwitterConnectHelper();
		//Verbindungsaufbau mit hardcodet Account(keine PIN-Eingabe)
		twitterService = twitterConnectHelper.getTwitterService();
		twitterStream = twitterConnectHelper.getTwitterStream();
		
		this.funnyTexts = funnyTexts;
		this.botAccount = botAccount.toLowerCase();
		myID = this.getMyID();
	}

	public void startUserStream(){
		userToListen = botAccount;
		twitterStream.addListener(userListener);
		twitterStream.filter(new FilterQuery(new long[]{getMyID()}));
	}
	
	public void startUserStream(String user){
		userToListen = user;
		startUserStream(getUsersID(user));
	}
	
	private void startUserStream(long userID){
		
		twitterStream.addListener(userListener);
		twitterStream.filter(new FilterQuery(new long[]{userID}));
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
	
	private long systemTime(){
		
		return new Date().getTime();
	}
	
	private void sendAnswerToListenedUser(String aktStatusText) {
		
		String antwort = "";
		antwort = funnyTexts.get(aktStatusText);
					
		if(antwort != null){
			if(!antwort.equals("1970")){

				System.out.println("Antwort: "+"@"+userToListen+" "+antwort);
				sendMessage("@"+userToListen+" "+antwort);
			}else{
				//the milliseconds since January 1, 1970, 00:00:00 GMT.
				sendMessage("@"+userToListen+" "+systemTime()+" (the milliseconds since January 1, 1970, 00:00:00 GMT.)");
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
	private boolean isUserSpeakingWithMe(Status aktStatus) {
		
		Logger.getLogger(TBController.class.getName()).log(Level.INFO, "aktStatusReplyID = {0}, \nMyID = {1}", new Object[]{aktStatus.getInReplyToUserId(), myID});
		if (aktStatus.getInReplyToUserId()==myID) {
			return true;

		}else{
			System.out.println("Noone's speaking with me :/");
		}
		return false;
	}
	
	UserStreamListener userListener = new UserStreamListener(){

		@Override
		public void onDeletionNotice(long l, long l1) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFriendList(long[] longs) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFavorite(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnfavorite(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFollow(User user, User user1) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onRetweet(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onDirectMessage(DirectMessage dm) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberAddition(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberDeletion(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListSubscription(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUnsubscription(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListCreation(User user, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUpdate(User user, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListDeletion(User user, UserList ul) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserProfileUpdate(User user) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onBlock(User user, User user1) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnblock(User user, User user1) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onStatus(Status status) {
			System.out.println("AktStatus: "+status.getText());
			System.out.println("FromUser: "+status.getUser().getScreenName());
			System.out.println("AktStatusraw: "+status);
			
			if(isUserSpeakingWithMe(status)){
				String aktStatusText = "";
				
				aktStatusText = status.getText().replaceAll(" ", "_").toLowerCase();
				aktStatusText = aktStatusText.replaceAll("@"+botAccount+"_", "");

				System.out.println("AktStatusTextAfterFormat: "+aktStatusText);
				
				sendAnswerToListenedUser(aktStatusText);
			}
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice sdn) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onTrackLimitationNotice(int i) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onScrubGeo(long l, long l1) {
			java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onException(Exception excptn) {
			if(excptn instanceof TwitterException){
				java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "is twitter exception");
				excptn.printStackTrace();
			}else{
				java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(java.util.logging.Level.SEVERE, "is NO twitter exception");
				excptn.printStackTrace();
			}
		}
		

    };
}
