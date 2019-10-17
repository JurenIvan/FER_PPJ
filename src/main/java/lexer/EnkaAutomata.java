package lexer;

import java.util.Set;

@SuppressWarnings("javadoc")
public interface EnkaAutomata {
	
	void initialize();

	Set<EnkaState> getCurrentStates();

	boolean isAnyStateAcceptable();
	
	void doTransitions(char c);
}
