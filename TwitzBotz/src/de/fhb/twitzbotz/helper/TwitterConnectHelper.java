
package de.fhb.twitzbotz.helper;

import de.fhb.twitzbotz.controller.TBController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author Michael Koppen
 */
public class TwitterConnectHelper {
	private AccessToken accessToken = null;
	private TwitterFactory twitterFactory = null;
	
	//Keys die die App identifizieren
	private final String consumerKey = "XofYnF58nnR1fBIwGq3dQ";
	private final String consumerKeySecure = "XtXFcPUzhjQAoDTRQTA7jm3Pw2m3IRX1fDf3kALqBUg";
	
	//Keys die den Account des Users identifizieren
	private final String token = "403358935-CXqlVYe8nKLBm9buxU55vES9HSBdgG5fbCLfOo";
	private final String tokenSecret = "2W6d3aNWLYTLcxWCsXDoBesDsiJADh7B0iWxERa9AnU";

	public TwitterConnectHelper() {
		describeEnviroment();
		
		twitterFactory = new TwitterFactory();
	}
	/**
	 * Verbindungsaufbau zu Twitter mit beliebigem Account-Token(mit PIN-Eingabe).
	 */
	public Twitter connectToServiceWithPIN() throws TwitterException, IOException{
		describeEnviroment();
		
		RequestToken requestToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Twitter twitterService = twitterFactory.getInstance();

		twitterService.setOAuthConsumer(consumerKey, consumerKeySecure);

		try {
			requestToken = twitterService.getOAuthRequestToken();
			System.out.println("Open the following URL and grant access to your account: ");

			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN: ");

			String pin = br.readLine();


			accessToken = twitterService.getOAuthAccessToken(requestToken, pin);
			
			this.storeAccessToken(accessToken);

		} catch (TwitterException te) {
			handleTwitterException(te);
		} catch (IOException ex) {
			System.err.println("Unknown input-output-error occured.");
			java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		return twitterService;
	}
	/**
	 * Verbindungsaufbau zu Twitter mit hardcodet access Tokens.
	 */
	public Twitter connectToServiceSingleAcc() {
		describeEnviroment();
				
		Twitter twitterService = twitterFactory.getInstance();

		AccessToken givenAccessToken = new AccessToken(token, tokenSecret);
		twitterService.setOAuthConsumer(consumerKey, consumerKeySecure);
		twitterService.setOAuthAccessToken(givenAccessToken);

		
		return twitterService;
	}

	private void storeAccessToken(AccessToken accessToken) throws TwitterException {
		describeEnviroment();
		
		System.out.println("Screenname: " + accessToken.getScreenName());
		System.out.println("UserID-AT: " + accessToken.getUserId());
		System.out.println("AToken: " + accessToken.getToken());
		System.out.println("ATokenSecret: " + accessToken.getTokenSecret());
	}
	
	private void describeEnviroment() {
		StackTraceElement stackTop = new Exception().getStackTrace()[1];
		java.util.logging.Logger.getLogger(TwitterConnectHelper.class.getName()).log(Level.INFO, "Logger: class = {0},\n method: {1}", new Object[]{stackTop.getClassName(), stackTop.getMethodName()});
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
				System.err.println("Unknown twitter-error occured.");
				java.util.logging.Logger.getLogger(TBController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			}
	}
}
