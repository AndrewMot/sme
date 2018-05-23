package sme.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import sme.model.Pattern;
import sme.model.Result;
import sme.util.Utility;

/**
 * Class for thread that process a url given and prints a output file with the
 * found matches
 * 
 * @author Andrés Motavita
 *
 */
public class ProcessURL implements Runnable {
	/**
	 * Thread name
	 */
	private String name;
	/**
	 * POJO result
	 */
	private Result res;
	/**
	 * List of patterns
	 */
	private List<Pattern> patterns;
	/**
	 * Logger
	 */
	private final Logger log = Logger.getLogger(ProcessURL.class);

	/**
	 * Construct
	 * 
	 * @param name
	 * @param uRL
	 * @param patterns
	 */
	public ProcessURL(String name, String uRL, List<Pattern> patterns) {
		this.res = new Result(uRL);
		this.name = name;
		this.patterns = patterns;
	}

	/**
	 * Thread body
	 */
	@Override
	public void run() {
		String message = String.format("Thread: %s Start. URL to process: %s Process: %s",
				Thread.currentThread().getName(), this.res.getURL(), this.name);
		log.info(message);

		if (!loadWebFromURL()) {
			message = String.format(
					"Thread: %s Interrupted. Process: %s. There is nothing to process. Probably not HTTPS protocol.",
					Thread.currentThread().getName(), this.name);
			log.warn(message);
			return;
		}
		findMatches();
		try {
			String outputFile = String.format("%s%s.%s", Utility.DEFAULT_OUTPUT_FILE, this.name,
					Utility.FILE_EXTENSION);
			writeMatches(new BufferedWriter(new FileWriter(outputFile)));
		} catch (IOException e) {
			message = String.format("Thread: %s Process: %s - Error writing in output. Error: %s",
					Thread.currentThread().getName(), this.name, e.getMessage());
			log.error(message);
		}
		message = String.format("Thread: %s Done. Process: %s", Thread.currentThread().getName(), this.name);
		log.info(message);
	}

	/**
	 * Print in a output file the result
	 * 
	 * @param out
	 */
	private void writeMatches(BufferedWriter out) {
		String line = String.format("Matches for %s%s", res.getURL(), System.lineSeparator());
		try {
			out.write(line);
			for (Entry<String, List<String>> o : res.getMatches().entrySet()) {
				line = String.format("Found %d matches for %s%s", o.getValue().size(), o.getKey(),
						System.lineSeparator());
				out.write(line);
				for (String s : o.getValue()) {
					line = String.format("%s%s", s, System.lineSeparator());
					out.write(line);
				}
			}
		} catch (Exception e) {
			String message = String.format("Thread: %s Process: %s - Error writing in output. Error: %s",
					Thread.currentThread().getName(), this.name, e.getMessage());
			log.error(message);
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				String message = String.format(
						"Thread: %s Process: %s - Error flushing and closing output file. Error: %s",
						Thread.currentThread().getName(), this.name, e.getMessage());
				log.error(message);
			}
		}
	}

	/**
	 * Look for all matches. If a proper name pattern is given, this method will use
	 * NER Implementation because a proper name is not recognizable with a string
	 * pattern. Words such as This, I, The Rain should not be taken as proper names.
	 */
	public void findMatches() {
		String message = String.format("Thread: %s Process: %s - Start Finding Matches with given patterns",
				Thread.currentThread().getName(), this.name);
		log.info(message);
		Pattern properName = new Pattern(Utility.PROPER_NAME_PATTERN_TITLE, Utility.PROPER_NAME_PATTERN);
		if (this.patterns.contains(properName)) {
			message = String.format("Thread: %s Process: %s - There is a Proper Name Pattern to process.",
					Thread.currentThread().getName(), this.name);
			log.info(message);
			this.patterns.remove(properName);
			try {
				res.getMatches().put(Utility.PROPER_NAME_PATTERN_TITLE,
						new LinkedList<String>(NERImp.getMatches(res.getWebTextFromURL())));

			} catch (ClassCastException | ClassNotFoundException | IOException e) {
				message = String.format(
						"Thread: %s Process: %s - Error obtaining matches for proper name pattern. Error: %s",
						Thread.currentThread().getName(), this.name, e.getMessage());
				log.error(message);
			}
		}

		String words[] = null;
		List<String> mat = null;
		for (Pattern pattern : this.patterns) {
			words = res.getWebTextFromURL().split(" ");
			mat = new LinkedList<>();
			for (String w : words) {
				if (!w.isEmpty() && pattern.matches(w))
					mat.add(w);
			}
			if (!mat.isEmpty())
				this.res.getMatches().put(pattern.getName(), mat);
		}
		message = String.format("Thread: %s Process: %s - Finished finding Matches with given patterns",
				Thread.currentThread().getName(), this.name);
		log.info(message);
	}

	/**
	 * Load web text from a WebSite. It uses JSoup component to extract only text
	 * from a website without html, javascript or css text
	 * 
	 * @return
	 */
	public boolean loadWebFromURL() {
		String message = null;
		try {
			message = String.format("Thread: %s Process: %s - Start loading text from URL %s",
					Thread.currentThread().getName(), this.name, this.res.getURL());
			log.info(message);
			this.res.setWebTextFromURL(Jsoup.connect(this.res.getURL()).get().text());
			message = String.format("Thread: %s Process: %s - Finished loading text from URL %s",
					Thread.currentThread().getName(), this.name, this.res.getURL());
			log.info(message);
			return !res.getWebTextFromURL().isEmpty();
		} catch (Exception e) {
			message = String.format("Thread: %s Process: %s - Error obtaining text from web with URL %s. Error: %s",
					Thread.currentThread().getName(), this.name, this.res.getURL(), e.getMessage());
			log.error(message);
		}
		return false;
	}

	/**
	 * To String
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
