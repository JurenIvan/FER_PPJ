package lexer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("javadoc")
public class Rule implements Serializable {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5880810685971608901L;
	
	private String regex;
	private EnkaGraph enkaGraph;
	private String tokenType;
	private List<String> commands;

	public Rule() {
		this.commands = new ArrayList<>();
	}

	public Rule(String regex, String tokenType) {
		this.regex = regex;
		this.enkaGraph = new EnkaGraph(regex);
		this.tokenType = tokenType;
		this.commands = new ArrayList<>();
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		this.enkaGraph = new EnkaGraph(regex);
	}

	public EnkaGraph getEnkaGraph() {
		return enkaGraph;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void addCommand(String command) {
		commands.add(command);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Rule))
			return false;
		Rule rule = (Rule) o;
		return regex.equals(rule.regex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(regex);
	}
}
