package lexer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("javadoc")
public class EnkaGraph implements Serializable {

	private static final long serialVersionUID = -140750338988255786L;
	private int nextStateNumber = 1;

	public static final String startNodeName = "S";
	public static final String endNodeName = "F";
	public final static char EPSILON = (char) 0;

	private final EnkaState startState = new EnkaState(startNodeName);
	private final EnkaState endState = new EnkaState(endNodeName);
	private Map<String, EnkaState> states;

	public EnkaGraph() {
		states = new HashMap<>();
		this.addNode(startState).addNode(endState);
		endState.setAcceptable(true);
	}

	public EnkaGraph(String regex) {
		this();
		addRegexTransition(regex);
	}

	private void addRegexTransition(String regex) {
		char[] regexContent= regex.toCharArray();
 		for(int i =0;i<regex.length();i++) {
			if(regexContent[i]=='\\') {
				i++;
				continue;
			}
			if(regexContent[i]=='$') {
				regexContent[i]=(char)0;
			}
		}

		addNormalizedRegexTransition(startState, endState, new String(regexContent));
	}

	public static String getStartNodeName() {
		return startNodeName;
	}

	public static String getEndNodeName() {
		return endNodeName;
	}

	/**
	 * Help method to get the first string inside respective parentheses, starting
	 * from given index. For example, "(123(456)789)" would return "456" if called
	 * with starting index 4. Note that the starting index needs to be at an opening
	 * parentheses symbol and the given string needs to have an respective closing
	 * parentheses symbol.
	 *
	 * @param s     the string with parentheses
	 * @param index the index of an opening parentheses symbol
	 * @return the string between parentheses starting at index
	 * @throws IllegalArgumentException if invalid index or string given
	 */
	private static String getStringBetweenParentheses(String s, int index) {

		if (s.length() <= index + 1) {
			throw new IllegalArgumentException("Invalid index given");
		}
		if (s.charAt(index) != '(') {
			throw new IllegalArgumentException("String must start with an opening parentheses.");
		}

		int parenthesesCounter = 1;
		for (int i = index + 1; i < s.length(); i++) {
			if (s.charAt(i) == '\\') {
				i++;
				continue;
			}

			if (s.charAt(i) == '(') {
				parenthesesCounter++;
			} else if (s.charAt(i) == ')') {
				parenthesesCounter--;
				if (parenthesesCounter == 0) {
					return s.substring(index + 1, i);
				}
			}
		}

		throw new IllegalArgumentException("Could not find an respective closing parentheses.");
	}

	public EnkaGraph addNode(EnkaState node) {
		if (states.containsKey(node.getName())) {
			throw new IllegalArgumentException(
					"Node with name " + node.getName() + " has already been added to graph nodes.");
		}

		states.put(node.getName(), node);
		return this;
	}

	public Map<String, EnkaState> getStates() {
		return states;
	}

	public void setStates(Map<String, EnkaState> states) {
		this.states = states;
	}

	public EnkaState getStartState() {
		return startState;
	}

	public EnkaState getEndState() {
		return endState;
	}

	private void addNormalizedRegexTransition(final EnkaState from, final EnkaState to, String regex) {
		if (regex.length() == 0) {
			throw new IllegalArgumentException("Empty string given.");
		}

		if (regex.length() == 1) {
			char c = regex.charAt(0);
			if (c == EPSILON) {
				from.addTransition(EPSILON, to);
			} else {
				from.addTransition(c, to);
			}
			return;
		}

		String previous = "";
		EnkaState lastState = from;
		for (int i = 0; i < regex.length(); i++) {

			if (regex.charAt(i) == '*') {
				if (previous.isEmpty()) {
					throw new IllegalArgumentException("Illegal use of * operator.");
				}

				EnkaState a = getNextState();
				EnkaState b = getNextState();

				EnkaState nextState;
				boolean beforeVertical = regex.length() - 1 == i || regex.charAt(i + 1) == '|';
				if (beforeVertical) {
					i++;
					nextState = to;
				} else {
					nextState = getNextState();
				}
				addNormalizedRegexTransition(lastState, nextState, String.valueOf(EPSILON));
				addNormalizedRegexTransition(lastState, a, String.valueOf(EPSILON));
				addNormalizedRegexTransition(a, b, previous);
				addNormalizedRegexTransition(b, a, String.valueOf(EPSILON));
				addNormalizedRegexTransition(b, nextState, String.valueOf(EPSILON));

				if (beforeVertical) {
					lastState = from;
				} else {
					lastState = nextState;
				}

				previous = "";
				continue;
			}

			if (regex.charAt(i) == '|') {
				addNormalizedRegexTransition(lastState, to, previous);
				lastState = from;
				previous = "";
				continue;
			}

			if (!previous.isEmpty()) {
				EnkaState nextState = getNextState();
				addNormalizedRegexTransition(lastState, nextState, previous);
				lastState = nextState;
				previous = "";
			}

			if (regex.charAt(i) == '\\') {
				char succeedingChar = regex.charAt(++i);
				switch (succeedingChar) {
				case '\\':
					previous = "\\";
					break;

				case 'n':
					previous = "\n";
					break;

				case 't':
					previous = "\t";
					break;

				case '_':
					previous = " ";
					break;

				default:
					previous = String.valueOf(succeedingChar);
				}
				continue;
			}

			if (regex.charAt(i) == '(') {
				String betweenParentheses = getStringBetweenParentheses(regex, i);
				previous = betweenParentheses;

				i += betweenParentheses.length() + 1;
				continue;
			}

			previous = String.valueOf(regex.charAt(i));
		}

		if (!previous.isEmpty()) {
			addNormalizedRegexTransition(lastState, to, previous);
		}
	}

	private EnkaState getNextState() {
		EnkaState enkaState = new EnkaState("q" + nextStateNumber++);
		addNode(enkaState);
		return enkaState;
	}

}