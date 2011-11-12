package de.fhb.twitzbotz.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.Status;

/**
 *
 * @author Michael Koppen
 */
public class TBControllerTest {
	
	public TBControllerTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
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
	public void testIsUserSpeakingWithMe() {
		System.out.println("isUserSpeakingWithMe");
		Status aktStatus = null;
		TBController instance = null;
		boolean expResult = false;
		boolean result = instance.isUserSpeakingWithMe(aktStatus);
		assertEquals(expResult, result);
	}

	/**
	 * Test of sendAnswerToListenedUser method, of class TBController.
	 */
	@Test
	public void testSendAnswerToListenedUser() {
		System.out.println("sendAnswerToListenedUser");
		Status status = null;
		TBController instance = null;
		instance.sendAnswerToListenedUser(status);
	}
}
