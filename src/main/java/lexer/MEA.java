package lexer;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MEA {

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
