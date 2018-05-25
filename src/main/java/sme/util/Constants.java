package sme.util;

import sme.controller.ProcessURL;

/**
 * utility class with constants
 * @author Andrés Motavita
 *
 */
public class Constants {
	/**
	 * Thread Pool size
	 */
	public static final int POOL_SIZE = 10;
	/**
	 * Default name for input file
	 */
	public static final String DEFAULT_INPUT_FILE = "in";
	/**
	 * File extension
	 */
	public static final String FILE_EXTENSION = "txt";
	/**
	 * Default name for output files
	 */
	public static final String DEFAULT_OUTPUT_FILE = "out_";
	/**
	 * Name for threads
	 */
	public static final String THREAD_NAME = "URLProcessor";
	/**
	 * Message for not input file
	 */
	public static final String NOT_INPUT_FILE = "Not Input File";
	/**
	 * Hashtag pattern
	 */
	public static final String HASHTAG_PATTERN = "(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)";
	/**
	 * Twitter pattern
	 */
	public static final String TWITTER_ACCOUNT_PATTERN = "@(\\w+)";
	/**
	 * Flag to identify proper name pattern.
	 * @see {@link ProcessURL#findMatches()}
	 * @see {@link NERImp}
	 */
	public static final String PROPER_NAME_PATTERN = "_NAME_";
	/**
	 * Proper name pattern 
	 */
	public static final String PROPER_NAME_PATTERN_TITLE = "Proper Name";
		
}
