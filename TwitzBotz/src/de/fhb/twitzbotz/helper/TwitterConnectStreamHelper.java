package de.fhb.twitzbotz.helper;

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
	private TwitterStreamFactory twitterStreamFactory = null;
	
	public TwitterConnectStreamHelper(){
		twitterStreamFactory = new TwitterStreamFactory();
		
	}
	
	public TwitterStream connectToStream(){
		return (TwitterStream) super.connect((OAuthSupport)twitterStreamFactory.getInstance());
	}
}