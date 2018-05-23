/**
 * 
 */
package sme.controller;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

/**
 * @author Andrés Motavita
 *
 */
public class TestProcessURL {

	/**
	 * Test method for {@link sme.controller.ProcessURL#run()}.
	 */
	@Test
	public void testRun() {
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#findMatches()}.
	 */
	@Test
	public void testFindMatches() {
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#loadWebFromURL()}.
	 */
	@Test
	public void testLoadWebFromURL() {
		String URL = "http://www.extremetech.com/";
		ProcessURL pu = new ProcessURL("", URL, new LinkedList<>());
		assertTrue(pu.loadWebFromURL());		
	}
	/**
	 * Test method for {@link sme.controller.ProcessURL#loadWebFromURL()}.
	 */
	@Test
	public void testLoadWebFromURLWrongProtocol() {
		String URL = "www.extremetech.com/";
		ProcessURL pu = new ProcessURL("", URL, new LinkedList<>());
		assertFalse(pu.loadWebFromURL());		
	}

}
