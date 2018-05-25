/**
 * 
 */
package sme.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import sme.exception.NotInputFileException;
import sme.model.Pattern;
import sme.util.Constants;

/**
 * @author Andrés Motavita
 *
 */
public class TestProcessURL {

	/**
	 * Test method for {@link sme.controller.ProcessURL#run()}.
	 * @throws NotInputFileException 
	 */
	@Test
	public void testRun() throws NotInputFileException {
		ExecutorService executor = Executors.newFixedThreadPool(Constants.POOL_SIZE);
		String in = System.getProperty("inputFile");
		String defaultIn = String.format("%s.%s", Constants.DEFAULT_INPUT_FILE, Constants.FILE_EXTENSION);
		in = in == null || in.isEmpty()
				? Thread.currentThread().getContextClassLoader().getResource(defaultIn).getPath()
				: in;
		FileLoader fL = new FileLoader(in);
		fL.init();
		fL.loadFile();
		Pattern properName = new Pattern(Constants.PROPER_NAME_PATTERN_TITLE, Constants.PROPER_NAME_PATTERN);
		List<Pattern> lP = new LinkedList<>();
		lP.add(properName);
		int i = 0;
		while (!fL.getUrls().isEmpty()) {
			String threadName = String.format("%s %d", Constants.THREAD_NAME, i++);
			Runnable pUThread = new ProcessURL(threadName, fL.getUrls().poll(), lP);
			executor.execute(pUThread);
			
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		assertTrue("Finished", executor.isTerminated());
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#findMatches()}.
	 */
	@Test
	public void testFindMatches() {
		String hashTagName = "HASH TAG";
		Pattern properName = new Pattern(hashTagName, Constants.HASHTAG_PATTERN);
		List<Pattern> lP = new LinkedList<>();
		lP.add(properName);
		ProcessURL pu = new ProcessURL("", "", lP);
		String text = "I go to school at #Stanford #University, which is located in #California.";
		pu.getRes().setWebTextFromURL(text);
		pu.findMatches();
		assertTrue("Key hashtag", pu.getRes().getMatches().containsKey(hashTagName));
		String[] a = { "#Stanford", "#University", "#California" };
		assertArrayEquals("Matches found", pu.getRes().getMatches().get(hashTagName).toArray(), a);
	}

	/**
	 * Test method for
	 * {@link sme.controller.ProcessURL#findMatchesForProperNamePattern()}.
	 */
	@Test
	public void testFindMatchesForProperNamePattern() {
		Pattern properName = new Pattern(Constants.PROPER_NAME_PATTERN_TITLE, Constants.PROPER_NAME_PATTERN);
		List<Pattern> lP = new LinkedList<>();
		lP.add(properName);
		ProcessURL pu = new ProcessURL("", "", lP);
		String text = "I go to school at Stanford University, which is located in California.";
		pu.getRes().setWebTextFromURL(text);
		pu.findMatchesForProperNamePattern();
		assertTrue("Key proper name", pu.getRes().getMatches().containsKey(Constants.PROPER_NAME_PATTERN_TITLE));
		String[] a = { "Stanford University", "California" };
		assertArrayEquals("Matches found", pu.getRes().getMatches().get(Constants.PROPER_NAME_PATTERN_TITLE).toArray(),
				a);
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
