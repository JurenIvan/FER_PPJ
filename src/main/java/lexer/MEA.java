package lexer;

import java.util.List;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;

@SuppressWarnings("javadoc")
public class MEA implements Serializable {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 1733977515060192487L;

	private List<EnkaAutomata> enkaAutomataList;

	public MEA(List<EnkaGraph> enkaGraphList) {
		this.enkaAutomataList = enkaGraphList.stream().map(EnkaAutomata::new).collect(toList());
	}

	public int parseNext(char c) {
		int first = -1;
		for (int i = 0; i < enkaAutomataList.size(); i++) {
			EnkaAutomata automata = enkaAutomataList.get(i);
			automata.doTransitions(c);
			if (first < 0 && automata.isAnyStateAcceptable()) {
				first = i;
			}
			if (!automata.getCurrentStates().isEmpty()) {
				if (first == -1) {
					first = -2;
				}
			}
		}
		return first;
	}

	public void reset() {
		for (int i = 0; i < enkaAutomataList.size(); i++) {
			enkaAutomataList.get(i).reset();
		}
	}
}
