/**
 * 
 */
package sme.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sme.exception.NotInputFileException;
import sme.model.Pattern;
import sme.util.Constants;

/**
 * @author Andrés Motavita
 *
 */
public class TestProcessURL {
	/**
	 * Mock for filesystem
	 */
	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	/**
	 * Test method for {@link sme.controller.ProcessURL#run()}.
	 * 
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
		FileLoader fileLoader = new FileLoader(in);
		fileLoader.init();
		fileLoader.loadFile();
		Pattern properName = new Pattern(Constants.PROPER_NAME_PATTERN_TITLE, Constants.PROPER_NAME_PATTERN);
		List<Pattern> patternList = new LinkedList<>();
		patternList.add(properName);
		int i = 0;
		while (!fileLoader.getUrls().isEmpty()) {
			String threadName = String.format("%s %d", Constants.THREAD_NAME, i++);
			Runnable processorUrlThread = new ProcessURL(threadName, fileLoader.getUrls().poll(), patternList);
			executor.execute(processorUrlThread);

		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		assertTrue("Finished", executor.isTerminated());
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#run()}.
	 * 
	 * @throws NotInputFileException
	 */
	@Test
	public void testRunNotWebText() throws NotInputFileException {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Pattern properName = new Pattern(Constants.PROPER_NAME_PATTERN_TITLE, Constants.PROPER_NAME_PATTERN);
		List<Pattern> patternList = new LinkedList<>();
		patternList.add(properName);
		String threadName = String.format("%s %d", Constants.THREAD_NAME, 1);
		Runnable processorUrlThread = new ProcessURL(threadName, "", patternList);
		executor.execute(processorUrlThread);
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		assertTrue("Finished", executor.isTerminated());
	}

	/**
	 * Test method for
	 * {@link sme.controller.ProcessURL#writeMatches(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteMatches() throws IOException {
		Hashtable<String, List<String>> result = new Hashtable<>();
		List<String> matches = new LinkedList<>();
		matches.add("Andres");
		matches.add("Motavita");
		result.put(Constants.PROPER_NAME_PATTERN_TITLE, matches);
		ProcessURL processorUrl = new ProcessURL("", "", new LinkedList<>());
		processorUrl.getRes().setMatches(result);
		String outputName = String.format("%s.%s", Constants.DEFAULT_OUTPUT_FILE, Constants.FILE_EXTENSION);
		tmp.create();
		String outputPath = tmp.newFile(outputName).getAbsolutePath();
		processorUrl.writeMatches(outputPath);
		assertTrue("Output file created", tmp.newFile(outputName).exists());
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#findMatches()}.
	 */
	@Test
	public void testFindMatches() {
		String hashTagName = "HASH TAG";
		Pattern properName = new Pattern(hashTagName, Constants.HASHTAG_PATTERN);
		List<Pattern> patternList = new LinkedList<>();
		patternList.add(properName);
		ProcessURL processorUrl = new ProcessURL("", "", patternList);
		String text = "I go to school at #Stanford #University, which is located in #California.";
		processorUrl.getRes().setWebTextFromURL(text);
		processorUrl.findMatches();
		assertTrue("Key hashtag", processorUrl.getRes().getMatches().containsKey(hashTagName));
		String[] expectedResult = { "#Stanford", "#University", "#California" };
		assertArrayEquals("Matches found", processorUrl.getRes().getMatches().get(hashTagName).toArray(),
				expectedResult);
	}

	/**
	 * Test method for
	 * {@link sme.controller.ProcessURL#findMatchesForProperNamePattern()}.
	 */
	@Test
	public void testFindMatchesForProperNamePattern() {
		Pattern properName = new Pattern(Constants.PROPER_NAME_PATTERN_TITLE, Constants.PROPER_NAME_PATTERN);
		List<Pattern> patternList = new LinkedList<>();
		patternList.add(properName);
		ProcessURL processorUrl = new ProcessURL("", "", patternList);
		String text = "I go to school at Stanford University, which is located in California.";
		processorUrl.getRes().setWebTextFromURL(text);
		processorUrl.findMatchesForProperNamePattern();
		assertTrue("Key proper name",
				processorUrl.getRes().getMatches().containsKey(Constants.PROPER_NAME_PATTERN_TITLE));
		String[] expectedResult = { "Stanford University", "California" };
		assertArrayEquals("Matches found",
				processorUrl.getRes().getMatches().get(Constants.PROPER_NAME_PATTERN_TITLE).toArray(), expectedResult);
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#loadWebFromURL()}.
	 */
	@Test
	public void testLoadWebFromURL() {
		String URL = "http://www.extremetech.com/";
		ProcessURL processorUrl = new ProcessURL("", URL, new LinkedList<>());
		assertTrue(processorUrl.loadWebFromURL());
	}

	/**
	 * Test method for {@link sme.controller.ProcessURL#loadWebFromURL()}.
	 */
	@Test
	public void testLoadWebFromURLWrongProtocol() {
		String URL = "www.extremetech.com/";
		ProcessURL processorUrl = new ProcessURL("", URL, new LinkedList<>());
		assertFalse(processorUrl.loadWebFromURL());
	}

}
