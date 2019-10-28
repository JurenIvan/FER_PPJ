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
    public final static char EPSILON = '\r';

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
        addTransition(startState, endState, regex);
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

    private void addTransition(final EnkaState from, final EnkaState to, String t) {
        if (t.length() == 0) {
            throw new IllegalArgumentException("Empty string given.");
        }

        if (t.length() == 1) {
            char c = t.charAt(0);
            if (c == '$') {
                // TODO $ and EPSILON should not be the same character in the graph
                from.addTransition(EPSILON, to);
            } else {
                from.addTransition(c, to);
            }
            return;
        }

        String previous = "";
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
            }

            if (!previous.isEmpty()) {
                EnkaState nextState = getNextState();
                addTransition(lastState, nextState, previous);
                lastState = nextState;
                previous = "";
            }

            if (t.charAt(i) == '\\') {
                char succeedingChar = t.charAt(++i);
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

            if (t.charAt(i) == '(') {
                String betweenParentheses = getStringBetweenParentheses(t, i);
                previous = betweenParentheses;

                i += betweenParentheses.length() + 1;
                continue;
            }

            previous = String.valueOf(t.charAt(i));
        }

        if (!previous.isEmpty()) {
            addTransition(lastState, to, previous);
        }
    }

    private EnkaState getNextState() {
		EnkaState enkaState = new EnkaState("q" + nextStateNumber++);
		addNode(enkaState);
		return enkaState;
    }


}