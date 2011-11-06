package de.fhb.twitzbotz.helper;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Der TwitterConnectServiceHelper ist zuständig für das anstossen des 
 * Verbindungsaufbaus zum Twitter-Service 
 * und liefert ein entsprechendes Objekt zurück.
 *
 * @author Michael Koppen
 */
public class TwitterConnectServiceHelper extends TwitterConnectHelperBase{
	private TwitterFactory twitterFactory = null;
	
	public TwitterConnectServiceHelper(){
		twitterFactory = new TwitterFactory();
		
	}
	
	public Twitter connectToService(){
		return super.connectToService(twitterFactory.getInstance());
	}
}