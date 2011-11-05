package de.fhb.twitzbotz.helper;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author Michael Koppen
 */
public class TwitterConnectStreamHelper extends TwitterConnectHelperBase{
	private TwitterStreamFactory twitterStreamFactory = null;
	
	public TwitterConnectStreamHelper(){
		twitterStreamFactory = new TwitterStreamFactory();
		
	}
	
	public TwitterStream connectToStream(){
		return super.connectToStream(twitterStreamFactory.getInstance());
	}
}