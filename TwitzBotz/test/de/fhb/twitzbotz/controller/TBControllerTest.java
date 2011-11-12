package de.fhb.twitzbotz.controller;

import de.fhb.twitzbotz.helper.LoadPropsHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import twitter4j.Status;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Koppen
 */
public class TBControllerTest {
	private static TBController tbc = null;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		LoadPropsHelper loadProps = new LoadPropsHelper();
		loadProps.loadAllProps();
		tbc = new TBController(loadProps.getFunnyTexts());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of isUserSpeakingWithMe method, of class TBController.
	 */
	@Test
	@Ignore
	public void testIsUserSpeakingWithMe() {
		System.out.println("isUserSpeakingWithMe");
		
		TBController instance = null;
		boolean expResult = false;
		Status aktStatus = null;
		boolean result = instance.isUserSpeakingWithMe(aktStatus);
		assertEquals(expResult, result);
	}

	/**
	 * Test of sendAnswerToListenedUser method, of class TBController.
	 */
	@Test
	@Ignore
	public void testSendAnswerToListenedUser() {
		System.out.println("sendAnswerToListenedUser");
		Status status = null;
		TBController instance = null;
		instance.sendAnswerToListenedUser(status);
	}

	/**
	 * Test of getAntwort method, of class TBController.
	 */
	@Test
	public void testGetAntwort() {
		System.out.println("getAntwort");
		String frage = "hallo";
		TBController instance = tbc;
		String expResult = "Hallo Du";
		String result = instance.getAntwort(frage);
		assertEquals(expResult, result);
	}
}
