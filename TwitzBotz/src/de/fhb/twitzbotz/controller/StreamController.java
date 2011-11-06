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
 * Der Streamcontroller verwaltet alle Funktionen rund um den Twitter-Stream.
 * z.B.:
 * - Empfangen von neuen Stati,
 * - Empfangen User-Profil-Updates,
 * - usw.
 *
 * @author Michael Koppen
 */
public class StreamController{
	private TwitterStream twitterStream = null;
	private TwitterConnectStreamHelper twitterConnectStreamHelper = null;
	private TBController parentController = null;
	
	
	public StreamController(TBController parentController){
		this.parentController = parentController;
		
		
		twitterConnectStreamHelper = new TwitterConnectStreamHelper();
		twitterStream = twitterConnectStreamHelper.connectToStream();
		twitterStream.addListener(userListener);
	}
	
	public void startUserStream(long userToListenID){
		twitterStream.filter(new FilterQuery(new long[]{userToListenID}));
	}
	
	
	
	private UserStreamListener userListener = new UserStreamListener(){

		@Override
		public void onDeletionNotice(long l, long l1) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFriendList(long[] longs) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFavorite(User user, User user1, Status status) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnfavorite(User user, User user1, Status status) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onFollow(User user, User user1) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onRetweet(User user, User user1, Status status) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onDirectMessage(DirectMessage dm) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberAddition(User user, User user1, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListMemberDeletion(User user, User user1, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListSubscription(User user, User user1, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUnsubscription(User user, User user1, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListCreation(User user, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListUpdate(User user, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserListDeletion(User user, UserList ul) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUserProfileUpdate(User user) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onBlock(User user, User user1) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onUnblock(User user, User user1) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onStatus(Status status) {
			System.out.println("AktStatus: "+status.getText());
			System.out.println("FromUser: "+status.getUser().getScreenName());
			System.out.println("AktStatusraw: "+status);
			
			if(parentController.isUserSpeakingWithMe(status)){
				parentController.sendAnswerToListenedUser(status);
			}
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice sdn) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}
		//########################
		@Override
		public void onTrackLimitationNotice(int i) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}

		@Override
		public void onScrubGeo(long l, long l1) {
			Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, "Not supported yet.");
		}
		//########################
		//TODO richtiges Exceptionhandling
		@Override
		public void onException(Exception excptn) {
			if(excptn instanceof TwitterException){
				twitterConnectStreamHelper.handleTwitterException((TwitterException)excptn);
			}else{
				
				Logger.getLogger(StreamController.class.getName()).log(Level.SEVERE, null, excptn);
			}
		}
		

    };
}
