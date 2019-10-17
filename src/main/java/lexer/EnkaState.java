package lexer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("javadoc")
public class EnkaState implements Comparable<EnkaState>, Serializable {

	private static final long serialVersionUID = 2548575133784796786L;

	String name;
	private Map<Character, List<EnkaState>> transitions;
	private boolean acceptable;

	public EnkaState(String name) {
		this.name = name;
		transitions = new HashMap<>();
	}

	public void addTransition(char inputSymbol, EnkaState nextState) {
		// System.out.println("\t" + name + "-->" + inputSymbol + "-->" + nextState); //
		// TODO remove debug
		if (!transitions.containsKey(inputSymbol)) {
			transitions.put(inputSymbol, new ArrayList<EnkaState>());
		}
		transitions.get(inputSymbol).add(nextState);
	}

	public boolean hasTransition(char inputSymbol) {
//		if (Enka.EMPTY.equals(inputSymbol))
//			return true;

		return transitions.containsKey(inputSymbol);
	}

	public List<EnkaState> getTransitions(char inputSymbol) {
//		if (Enka.EMPTY.equals(inputSymbol))
//			return null;

		if (!hasTransition(inputSymbol))
			return null;
		// TODO better to return null or Collections.EMPTY_LIST?

		return transitions.get(inputSymbol);
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
		return getName().compareTo(obj.getName());
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
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}

}