package sme.model;

import java.util.Hashtable;
import java.util.List;

/**
 * POJO for process result
 * 
 * @author Andrés Motavita
 *
 */
public class Result {
	/**
	 * URL to be processed
	 */
	private String URL;
	/**
	 * Text obtained from Web with URL given
	 */
	private String webTextFromURL;
	/**
	 * hashtable with result of matches from patterns used in Web text where
	 * Hashtable key is the Pattern Title and Hashtable value is a list with the
	 * strings that matched the patterns given.
	 */
	private Hashtable<String, List<String>> matches;
	/**
	 * Construct
	 * @param uRL
	 */
	public Result(String uRL) {
		this.matches = new Hashtable<>();
		URL = uRL;
	}
	/**
	 * Get matches
	 * @return matches
	 */
	public Hashtable<String, List<String>> getMatches() {
		return matches;
	}
	/**
	 * Set Matches
	 * @param matches
	 */
	public void setMatches(Hashtable<String, List<String>> matches) {
		this.matches = matches;
	}
	/**
	 * Get Url
	 * @return Url
	 */
	public String getURL() {
		return URL;
	}
	/**
	 * Set URL
	 * @param uRL
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}
	/**
	 * Get text obtained from a Website
	 * @return web text
	 */
	public String getWebTextFromURL() {
		return webTextFromURL;
	}
	/**
	 * Set text from a Website
	 * @param webTextFromURL
	 */
	public void setWebTextFromURL(String webTextFromURL) {
		this.webTextFromURL = webTextFromURL;
	}

}
