package de.fhb.twitzbotz.helper;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
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