package de.fhb.twitzbotz.helper;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;

/**
 *
 * @author Michael Koppen
 */
public class TwitterConnectHelperBase {
	//Keys die die App identifizieren
	private final String consumerKey = "XofYnF58nnR1fBIwGq3dQ";
	private final String consumerKeySecure = "XtXFcPUzhjQAoDTRQTA7jm3Pw2m3IRX1fDf3kALqBUg";
	
	//Keys die den Account des Users identifizieren
	private final String token = "403358935-CXqlVYe8nKLBm9buxU55vES9HSBdgG5fbCLfOo";
	private final String tokenSecret = "2W6d3aNWLYTLcxWCsXDoBesDsiJADh7B0iWxERa9AnU";
	
	protected Twitter connectToService(Twitter twitter){
		AccessToken givenAccessToken = new AccessToken(token, tokenSecret);
		twitter.setOAuthConsumer(consumerKey, consumerKeySecure);
		twitter.setOAuthAccessToken(givenAccessToken);
		
		return twitter;
	}
	protected TwitterStream connectToStream(TwitterStream twitter){
		AccessToken givenAccessToken = new AccessToken(token, tokenSecret);
		twitter.setOAuthConsumer(consumerKey, consumerKeySecure);
		twitter.setOAuthAccessToken(givenAccessToken);
		
		return twitter;
	}
	
	
	public void handleTwitterException(TwitterException ex){
		
		if (400 == ex.getStatusCode()) {
			System.err.println("Rate limit exceeded. Clients may not make more than "+ex.getRateLimitStatus().getHourlyLimit()+" requests per hour. \nThe next reset is "+ex.getRateLimitStatus().getResetTime());
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(401 == ex.getStatusCode()){
			System.err.println("Authentication credentials were missing or incorrect.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(404 == ex.getStatusCode()){
			System.err.println("The URI requested is invalid or the resource requested, such as a user, does not exists.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(500 == ex.getStatusCode()){
			System.err.println("Something is broken. Please post to the group so the Twitter team can investigate.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(502 == ex.getStatusCode()){
			System.err.println("Twitter is down or being upgraded.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(503 == ex.getStatusCode()){
			System.err.println("The Twitter servers are up, but overloaded with requests. Try again later.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else if(-1 == ex.getStatusCode()){
			System.err.println("Can not connect to the internet or the host is down.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} else{
			System.err.println("Unknown twitter-error occured.");
			java.util.logging.Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
}
