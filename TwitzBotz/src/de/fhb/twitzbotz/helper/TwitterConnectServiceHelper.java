package de.fhb.twitzbotz.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthSupport;

/**
 * Der TwitterConnectServiceHelper ist zuständig für das anstossen des 
 * Verbindungsaufbaus zum Twitter-Service 
 * und liefert ein entsprechendes Objekt zurück.
 *
 * @author Michael Koppen
 */
public class TwitterConnectServiceHelper extends TwitterConnectHelperBase{
	private final static Logger LOGGER = Logger.getLogger(TwitterConnectServiceHelper.class.getName());
	
	private TwitterFactory twitterFactory = null;
	
	public TwitterConnectServiceHelper(){
		LOGGER.setLevel(Level.SEVERE);
		
		twitterFactory = new TwitterFactory();
		
	}
	
	public Twitter connectToService(){
		return (Twitter) super.connect((OAuthSupport)twitterFactory.getInstance());
	}
}