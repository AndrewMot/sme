package sme.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * Class for NER Implementation. Stanford NER is a Java implementation of a
 * Named Entity Recognizer. Named Entity Recognition (NER) labels sequences of
 * words in a text which are the names of things, such as person and company
 * names, or gene and protein names.
 * 
 * 
 * @author Andrés Motavita
 * @see {@link https://nlp.stanford.edu/software/CRF-NER.html}
 */
public class NERImp {
	/**
	 * Given a input string, returns a {@code List<String>} with all Proper Names
	 * identified
	 * 
	 * @param in
	 * @return {@code List<String>} with all Proper Names
	 * @throws ClassCastException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<String> getMatches(String in) throws ClassCastException, ClassNotFoundException, IOException {
		List<String> result = new LinkedList<>();
		if (!in.isEmpty()) {
			String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
			URL url = Thread.currentThread().getContextClassLoader().getResource(serializedClassifier);
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(url.getPath());
			String text = classifier.classifyToString(in, "tabbedEntities", false);
			String[] lines = text.split("\n");
			String[] parts = null;
			String name = null;
			for (String line : lines) {
				parts = line.split("\t");
				name = parts[0].trim();
				if (!name.isEmpty())
					result.add(name);
			}
		}
		return result;
	}
}
