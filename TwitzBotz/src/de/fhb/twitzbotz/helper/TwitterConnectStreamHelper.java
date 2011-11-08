package de.fhb.twitzbotz.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.OAuthSupport;

/**
 * Der TwitterConnectStreamHelper ist zuständig für das anstossen des 
 * Verbindungsaufbaus zum Twitter-Stream 
 * und liefert ein entsprechendes Objekt zurück.
 * 
 * @author Michael Koppen
 */
public class TwitterConnectStreamHelper extends TwitterConnectHelperBase{
	private final static Logger LOGGER = Logger.getLogger(TwitterConnectStreamHelper.class.getName());
	
	private TwitterStreamFactory twitterStreamFactory = null;
	
	public TwitterConnectStreamHelper(){
		LOGGER.setLevel(Level.SEVERE);
		
		twitterStreamFactory = new TwitterStreamFactory();
		
	}
	
	public TwitterStream connectToStream(){
		return (TwitterStream) super.connect((OAuthSupport)twitterStreamFactory.getInstance());
	}
}