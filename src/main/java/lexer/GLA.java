package lexer;

import java.io.IOException;

@SuppressWarnings("javadoc")
public class GLA {

	public static void main(String[] args) throws IOException {
		String regex1 = "a|b|c(a|b*|c)(a|b*|(c|d))*|d";
		String regex2 = "aaaa|aaaab|abaaaac|(((c)))|(d|ee*)|f";

		EnkaGraph graph = new EnkaGraph(regex2);
		Utils.runGraph(graph);

//		Utils.serializeObject("test.ser", graph);
//		EnkaGraph deserialized = Utils.deserializeObject("test.ser");
//		Utils.runGraph(deserialized);
	}

}
