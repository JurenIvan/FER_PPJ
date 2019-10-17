package lexer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("javadoc")
public class EnkaGraph implements Serializable, EnkaAutomata {

	private static final long serialVersionUID = -140750338988255786L;

	public final static char EPSILON = '$';
//	public final static char EMPTY = '#';

	private Map<String, EnkaState> states = new HashMap<>();
	private EnkaState startState;
	private EnkaState endState;

	public static final String startNodeName = "S";
	public static final String endNodeName = "F";

	public EnkaGraph() {
		startState = new EnkaState(startNodeName);
		endState = new EnkaState(endNodeName);

		addNode(startState);
		addNode(endState);
	}

	public EnkaGraph(String regex) {
		this();
		addTransition(startState, endState, regex);
	}

	public void addNode(EnkaState node) {
		if (states.containsKey(node.getName())) {
			throw new IllegalArgumentException(
					"Node with name " + node.getName() + " has already been added to graph nodes.");
		}

		states.put(node.getName(), node);
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

	public void setStartState(EnkaState startState) {
		this.startState = startState;
	}

	public EnkaState getEndState() {
		return endState;
	}

	public void setEndState(EnkaState endState) {
		this.endState = endState;
	}

	private void addTransition(final EnkaState from, final EnkaState to, String t) {
//		System.out.println(String.format("%2s,%2s,%2s", from.getName(), to.getName(), t)); // TODO remove debug

		if (t.length() == 0) {
			throw new IllegalArgumentException("Empty string given.");
		}

		if (t.length() == 1) {
			from.addTransition(t.charAt(0), to);
			return;
		}

		if (t.startsWith("(") && t.endsWith(")")) {
			addTransition(from, to, t.substring(1, t.length() - 1));
			return;
		}

		String previous = "";
//		int backslashCounter = 0; // TODO add escape support
		EnkaState lastState = from;
		for (int i = 0; i < t.length(); i++) {

			if (t.charAt(i) == '*') {
				if (previous.isEmpty()) {
					throw new IllegalArgumentException("Illegal use of * operator.");
				}

				EnkaState a = getNextState();
				EnkaState b = getNextState();

				EnkaState nextState;
				boolean beforeVertical = t.length() - 1 == i || t.charAt(i + 1) == '|';
				if (beforeVertical) {
					i++;
					nextState = to;
				} else {
					nextState = getNextState();
				}
				addTransition(lastState, nextState, String.valueOf(EPSILON));
				addTransition(lastState, a, String.valueOf(EPSILON));
				addTransition(a, b, previous);
				addTransition(b, a, String.valueOf(EPSILON));
				addTransition(b, nextState, String.valueOf(EPSILON));

				if (beforeVertical) {
					lastState = from;
				} else {
					lastState = nextState;
				}

				previous = "";
				continue;
			}

			if (t.charAt(i) == '|') {
				addTransition(lastState, to, previous);
				lastState = from;
				previous = "";
				continue;
			} else {
				if (!previous.isEmpty()) {
					EnkaState nextState = getNextState();
					addTransition(lastState, nextState, previous);
					lastState = nextState;
					previous = "";
				}
			}

			if (t.charAt(i) == '\\') {
				previous = String.valueOf(t.charAt(++i));
			} else if (t.charAt(i) == '(') {
				String betweenParentheses = getStringBetweenParentheses(t, i);
				previous = betweenParentheses;

				i += betweenParentheses.length() + 1;
			} else {
				previous = String.valueOf(t.charAt(i));
			}
		}

		if (!previous.isEmpty()) {
			addTransition(lastState, to, previous);
		}
	}

	public int nextStateNumber = 1;

	private EnkaState getNextState() {
		return new EnkaState("q" + nextStateNumber++);
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

	Set<EnkaState> currentStates;

	String simulate(String input) {
		if (startState == null) {
			throw new IllegalStateException("Simulation cannot be started if there is no starting state");
		}

		currentStates = new HashSet<>();
		currentStates.add(startState);
		doEpsilonTransitions();

		StringBuilder sb = new StringBuilder();
		sb.append(currentStatesString());

		for (char c : input.toCharArray()) {
			doTransitions(c);
			doEpsilonTransitions();
			sb.append('|' + currentStatesString());
		}

		return sb.toString();
	}

	private String currentStatesString() {
		if (currentStates.size() == 0)
			return "";

		return currentStates.stream().map(EnkaState::getName).reduce((x, y) -> (x + "," + y)).get();
	}

	@Override
	public void initialize() {
		// TODO as currentStates is a member variable, only one automata simulation can
		// be run at once without interference
		currentStates = new HashSet<>();
		currentStates.add(startState);
		doEpsilonTransitions();
	}

	@Override
	public void doTransitions(char symbol) {
		Set<EnkaState> nextStates = new HashSet<>();

		for (EnkaState e : currentStates) {
			if (e.hasTransition(symbol)) {
				nextStates.addAll(e.getTransitions(symbol));
			}
		}

		currentStates = nextStates;

		doEpsilonTransitions();
	}

	private void doEpsilonTransitions() {
		Set<EnkaState> nextStates = new HashSet<>();

		for (EnkaState e : currentStates) {
			if (e.hasTransition(EPSILON)) {
				nextStates.addAll(e.getTransitions(EPSILON));
			}
		}

		nextStates.addAll(currentStates);
		if (currentStates.size() == nextStates.size()) {
			currentStates = nextStates;
		} else {
			currentStates = nextStates;
			doEpsilonTransitions();
		}
	}

	@Override
	public Set<EnkaState> getCurrentStates() {
		return currentStates;
	}

	@Override
	public boolean isAnyStateAcceptable() {
		for (EnkaState state : currentStates) {
			if (state.isAcceptable()) {
				return true;
			}
		}

		return false;
	}

}