/**
 * 
 */
package sme.model;

import static org.junit.Assert.*;

import org.junit.Test;

import sme.util.Constants;

/**
 * @author Andrés Motavita
 *
 */
public class TestPattern {

	/**
	 * Test method for {@link sme.model.Pattern#matches(java.lang.String)}.
	 */
	@Test
	public void testMatches() {
		String patternName = "HashTag Pattern";
		Pattern pat = new Pattern(patternName, Constants.HASHTAG_PATTERN);
		String evaluate = "#DeleteThis";
		assertEquals("Same Match", evaluate.matches(Constants.HASHTAG_PATTERN), pat.matches(evaluate));
	}

}
