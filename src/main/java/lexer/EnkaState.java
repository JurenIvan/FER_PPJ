package lexer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("javadoc")
public class EnkaState implements Comparable<EnkaState>, Serializable {

    private static final long serialVersionUID = 2548575133784796786L;

    private String name;
    private Map<Character, List<EnkaState>> transitions;
    private boolean acceptable;

    public EnkaState(String name) {
        this.name = name;
        transitions = new HashMap<>();
    }

    public void addTransition(char inputSymbol, EnkaState nextState) {
        if (!transitions.containsKey(inputSymbol)) {
            transitions.put(inputSymbol, new ArrayList<>());
        }
        transitions.get(inputSymbol).add(nextState);
    }

    public boolean hasTransition(char inputSymbol) {
        return transitions.containsKey(inputSymbol);
    }

    public List<EnkaState> getTransitions(char inputSymbol) {
        return transitions.get(inputSymbol);
//		// TODO better to return null or Collections.EMPTY_LIST?
//		// although nicer to read, initialization is expensive so null is better performance wise
    }

    public Map<Character, List<EnkaState>> getAllTransitions() {
        return transitions;
    }

    public String getName() {
        return name;
    }

    public boolean isAcceptable() {
        return acceptable;
    }

    public void setAcceptable(boolean acceptable) {
        this.acceptable = acceptable;
    }

    @Override
    public int compareTo(EnkaState obj) {
        if (this == obj)
            return 0;
        if (obj == null)
            return -1;
        return name.compareTo(obj.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EnkaState other = (EnkaState) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}