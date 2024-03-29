package de.fhb.twitzbotz.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterBase;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthSupport;
import twitter4j.auth.RequestToken;

/**
 * Die TwitterConnectHelperBase ist zuständig für den 
 * Verbindungsaufbau zum Twitter-Stream oder Twitter-Service.
 *
 * @author Michael Koppen
 */
public class TwitterConnectHelperBase {
	private final static Logger LOGGER = Logger.getLogger(TwitterConnectHelperBase.class.getName());
	
	//Keys die die App identifizieren
	private final String consumerKey = "";
	private final String consumerKeySecure = "";
	
	//Keys die den Account des Users identifizieren
	private final String token = "";
	private final String tokenSecret = "";
	
	private ArrayList<TwitterBase> openedConnections = new ArrayList<TwitterBase>();

	public TwitterConnectHelperBase() {
		LOGGER.setLevel(Level.SEVERE);
		
	}
	
	/**
	 * Verbindungsaufbau zu Twitter mit beliebigem Account-Token(mit PIN-Eingabe).
	 * z.Z. nicht funktionstuechtig!
	 */
	private Twitter connectToServiceWithPIN(Twitter twitter) throws IOException{
		AccessToken accessToken = null;
		
		RequestToken requestToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		twitter.setOAuthConsumer(consumerKey, consumerKeySecure);

		try {
			requestToken = twitter.getOAuthRequestToken();
			System.out.println("Open the following URL and grant access to your account: ");

			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN: ");

			String pin = br.readLine();


			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			
			this.storeAccessToken(accessToken);

		} catch (TwitterException te) {
			handleTwitterException(te);
		}
		return twitter;
	}
	
	protected TwitterBase connect(OAuthSupport twitter){
		AccessToken givenAccessToken = new AccessToken(token, tokenSecret);
		twitter.setOAuthConsumer(consumerKey, consumerKeySecure);
		twitter.setOAuthAccessToken(givenAccessToken);
		
		openedConnections.add((TwitterBase)twitter);
		
		return (TwitterBase)twitter;
	}
	/**
	 * Methode zum Speichern eines AccessTokens.
	 * z.Z. nicht funktionstuechtig!
	 * 
	 * @param accessToken
	 */
	private void storeAccessToken(AccessToken accessToken){
		
		
		System.out.println("Screenname: " + accessToken.getScreenName());
		System.out.println("UserID-AT: " + accessToken.getUserId());
		System.out.println("AToken: " + accessToken.getToken());
		System.out.println("ATokenSecret: " + accessToken.getTokenSecret());
		
	}
	public void closeTwitter(){
		for (TwitterBase twitterBase : openedConnections) {
			twitterBase.shutdown();
		}
	}
	/**
	 * Methode um gebündelt Twitterexception zu behandeln und in Klartextmeldungen ausgeben zu koennen.
	 * 
	 * @param ex 
	 */
	public void handleTwitterException(TwitterException ex){
		if (400 == ex.getStatusCode()) {
			closeTwitter();
			System.err.println("Rate limit exceeded. Clients may not make more than "+ex.getRateLimitStatus().getHourlyLimit()+" requests per hour. \nThe next reset is "+ex.getRateLimitStatus().getResetTime());
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(401 == ex.getStatusCode()){
			closeTwitter();
			System.err.println("Authentication credentials were missing or incorrect.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(403 == ex.getStatusCode()){
			System.err.println("Duplicated status.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(404 == ex.getStatusCode()){
			System.err.println("The URI requested is invalid or the resource requested, such as a user, does not exists.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(406 == ex.getStatusCode()){
			System.err.println("Request returned - invalid format is specified in the request.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(420 == ex.getStatusCode()){
			closeTwitter();
			System.err.println("Too many logins with your account in a short time.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(500 == ex.getStatusCode()){
			System.err.println("Something is broken. Please post to the group so the Twitter team can investigate.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(502 == ex.getStatusCode()){
			closeTwitter();
			System.err.println("Twitter is down or being upgraded.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(503 == ex.getStatusCode()){
			closeTwitter();
			System.err.println("The Twitter servers are up, but overloaded with requests. Try again later.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else if(-1 == ex.getStatusCode()){
			closeTwitter();
			System.err.println("Can not connect to the internet or the host is down.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		} else{
			closeTwitter();
			System.err.println("Unknown twitter-error occured.");
			Logger.getLogger(TwitterConnectHelperBase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
