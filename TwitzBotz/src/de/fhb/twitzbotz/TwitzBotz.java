package de.fhb.twitzbotz;

import de.fhb.twitzbotz.controller.TBController;
import de.fhb.twitzbotz.helper.LoadPropsHelper;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *	TwitzBotz
 * 
 * TwitzBotz ist ein automatischer Twitter-Client.
 * Sie können TwitzBotz Twitter-User beobachten lassen. 
 * Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), 
 * wird TwitzBotz antworten.
 * 
 * @author Michael Koppen
 */
public class TwitzBotz {
	private final static Logger LOGGER = Logger.getLogger(TwitzBotz.class.getName());
	public static void main(String[] args) {
		TwitzBotz app = new TwitzBotz();
		app.init(args);

	}

	public void init(String[] args) {
		try {
			initLoggers(initRootLogger());

			LoadPropsHelper propsHelper = new LoadPropsHelper();
			propsHelper.loadAllProps();

			TBController tbController = new TBController(propsHelper.getFunnyTexts());

			tbController.startStream(propsHelper.getUserToListen());

		} catch (Exception e) {
			System.err.println("Unknown system-error occured.");
			LOGGER.log(Level.SEVERE, null, e);
		}
	}

	private void initLoggers(Logger rootLogger) {

		rootLogger.setLevel(Level.SEVERE);
		LOGGER.setLevel(Level.SEVERE);


	}

	private Logger initRootLogger() {
		Level consoleHandlerLevel = Level.OFF;
		Level fileHandlerLevel = Level.SEVERE;

		//setting up ConsoleHandler
		Logger rootLogger = Logger.getLogger("");

		Handler[] handlers = rootLogger.getHandlers();

		ConsoleHandler chandler = null;

		for (int i = 0; i < handlers.length; i++) {
			if (handlers[i] instanceof ConsoleHandler) {
				chandler = (ConsoleHandler) handlers[i];
			}
		}

		if (chandler != null) {
			chandler.setLevel(consoleHandlerLevel);
		} else {
			LOGGER.log(Level.SEVERE, "No ConsoleHandler there.");
		}

		//setting up FileHandler
		FileHandler fh = null;
		try {
			fh = new FileHandler("log/log_" + new Date() + ".xml");
			fh.setLevel(fileHandlerLevel);
		} catch (IOException ex) {
			new File("log").mkdir();
			try {
				fh = new FileHandler("log/log_" + new Date() + ".xml");
				fh.setLevel(fileHandlerLevel);
			} catch (IOException ex1) {
				System.err.println("Input-output-error while creating the initial log.");
				LOGGER.log(Level.SEVERE, null, ex1);
			} catch (SecurityException ex1) {
				LOGGER.log(Level.SEVERE, null, ex1);
			}
			Logger.getLogger(TwitzBotz.class.getName()).log(Level.SEVERE, null, ex);

		} catch (SecurityException ex) {
			System.err.println("Cannot open/access Log-Folder so I will not log anything.");
			LOGGER.log(Level.SEVERE, null, ex);
		}

		if (fh != null) {
			rootLogger.addHandler(fh);
		}
		return rootLogger;
	}
}
