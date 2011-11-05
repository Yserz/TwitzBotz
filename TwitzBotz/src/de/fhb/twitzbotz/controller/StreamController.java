package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.TwitterConnectStreamHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

/**
 *
 * @author Michael Koppen
 */
public class StreamController{
	private TwitterStream twitterStream = null;
	private TwitterConnectStreamHelper twitterConnectStreamHelper = null;
	private ServiceController serviceController = null;
	
	private long myID;
	
	public StreamController(ServiceController serviceController){
		this.serviceController = serviceController;
		this.myID = serviceController.getMyID();
		
		twitterConnectStreamHelper = new TwitterConnectStreamHelper();
		twitterStream = twitterConnectStreamHelper.connectToStream();
		twitterStream.addListener(userListener);
	}
	
	public void startUserStream(String userToListen){
		twitterStream.filter(new FilterQuery(new long[]{serviceController.getUsersID(userToListen)}));
	}
	
	/**
	 * TODO sprechender name
	 * 
	 * @param aktStatus written by userToListen
	 * @param lastStatus written by userToListen
	 * @return lastStatus written by userToListen(may changed)
	 */
	private boolean isUserSpeakingWithMe(Status aktStatus) {
		
		Logger.getLogger(StreamController.class.getName()).log(Level.INFO, "aktStatusReplyID = {0}, \nMyID = {1}", new Object[]{aktStatus.getInReplyToUserId(), myID});
		if (aktStatus.getInReplyToUserId()==myID) {
			return true;

		}else{
			System.out.println("Noone's speaking with me :/");
		}
		return false;
	}
	
	private UserStreamListener userListener = new UserStreamListener(){

		@Override
		public void onDeletionNotice(long l, long l1) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFriendList(long[] longs) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFavorite(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnfavorite(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFollow(User user, User user1) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onRetweet(User user, User user1, Status status) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onDirectMessage(DirectMessage dm) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberAddition(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberDeletion(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListSubscription(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUnsubscription(User user, User user1, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListCreation(User user, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUpdate(User user, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListDeletion(User user, UserList ul) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserProfileUpdate(User user) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onBlock(User user, User user1) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnblock(User user, User user1) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onStatus(Status status) {
			System.out.println("AktStatus: "+status.getText());
			System.out.println("FromUser: "+status.getUser().getScreenName());
			System.out.println("AktStatusraw: "+status);
			
			if(isUserSpeakingWithMe(status)){
				serviceController.sendAnswerToListenedUser(status);
			}
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice sdn) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onTrackLimitationNotice(int i) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onScrubGeo(long l, long l1) {
			java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "Not supported yet.");
		}
		//########################
		//TODO richtiges Exceptionhandling
		@Override
		public void onException(Exception excptn) {
			if(excptn instanceof TwitterException){
				java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "is twitter exception");
				excptn.printStackTrace();
			}else{
				java.util.logging.Logger.getLogger(StreamController.class.getName()).log(java.util.logging.Level.SEVERE, "is NO twitter exception");
				excptn.printStackTrace();
			}
		}
		

    };
}
