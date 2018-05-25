package sme.model;

/**
 * Class for Patterns used.
 * 
 * @author Andrés Motavita
 *
 */
public class Pattern {
	/**
	 * Pattern name
	 */
	private String name;
	/**
	 * Regex to match a string
	 */
	private String regex;

	/**
	 * Construct
	 * 
	 * @param name
	 * @param regex
	 */
	public Pattern(String name, String regex) {
		this.name = name;
		this.regex = regex;
	}

	/**
	 * 
	 * @return Pattern name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Pattern name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return Pattern Regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Set pattern regex
	 * 
	 * @param regex
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Pattern) {
			Pattern other = (Pattern) obj;
			return other.getName().equals(this.getName()) && other.getRegex().equals(this.getRegex());
		}
		return false;
	}

	/**
	 * Evaluates whether a string matches the established pattern
	 * 
	 * @param evaluate
	 * @return true when string matches the pattern, otherwise false
	 */
	public boolean matches(String evaluate) {
		return evaluate.matches(getRegex());
	}

}
