package de.fhb.twitzbotz.controller;

import java.util.HashMap;


/**
 * TODO Beschreibung TBController
 * 
 * @author Michael Koppen
 */
public class TBController {
	private ServiceController serviceController = null;
	private StreamController streamController = null;
	
	//TODO Make a List
	private String userToListen = "TwitBot2";

	public TBController(HashMap<String, String> funnyTexts) {
		
		serviceController = new ServiceController(funnyTexts);
		streamController = new StreamController(serviceController);
		
		startStreams();
		
	}
	private void startStreams(){
		//TODO Start a for-loop over the userlist(Anmerkung: nicht mehr als 6 Streams)
		streamController.startUserStream(userToListen);
	}

}
