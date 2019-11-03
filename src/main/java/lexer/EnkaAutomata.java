package lexer;

import static lexer.EnkaGraph.EPSILON;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("javadoc")
public class EnkaAutomata implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -5386476726898513120L;

	private Set<EnkaState> currentStates;
	private final EnkaGraph enkaGraph;

	public EnkaAutomata(EnkaGraph enkaGraph) {
		this.enkaGraph = enkaGraph;
	}

	public void reset() {
		currentStates = new HashSet<>();
		currentStates.add(enkaGraph.getStartState());
		doEpsilonTransitions();
	}

	public void doTransitions(char symbol) {
		Set<EnkaState> nextStates = new HashSet<>();

		if (currentStates == null) {
			System.out.println("dummy");
		}

		for (EnkaState e : currentStates) {
			if (e.hasTransition(symbol)) {
				nextStates.addAll(e.getTransitions(symbol));
			}
		}
		currentStates = nextStates;

		doEpsilonTransitions();
	}

	public Set<EnkaState> getCurrentStates() {
		return currentStates;
	}

	public boolean isAnyStateAcceptable() {
		for (EnkaState state : currentStates) {
			if (state.isAcceptable()) {
				return true;
			}
		}
		return false;
	}

	private void doEpsilonTransitions() {
		Set<EnkaState> lastDeltaStates = currentStates;
		boolean proceed;
		do {
			Set<EnkaState> deltaStates = new HashSet<>();

			for (EnkaState e : lastDeltaStates) {
				if (e.hasTransition(EPSILON)) {
					e.getTransitions(EPSILON).forEach(state -> {
						if (!currentStates.contains(state)) {
							deltaStates.add(state);
						}
					});
				}
			}
			lastDeltaStates = deltaStates;
			proceed = currentStates.addAll(deltaStates);
		} while (proceed);
	}
}
