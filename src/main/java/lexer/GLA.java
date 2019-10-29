package lexer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class GLA {

    private static List<String> lines = new ArrayList<>();
    private static List<String> states = new ArrayList<>();
    private static Set<String> tokenTypes = new HashSet<>();
    private static HashMap<String, List<Rule>> stateRulesMap;
    private static RegexResolver regexResolver;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()){
			lines.add(sc.nextLine());
		}
		sc.close();
		
		int definitionLinesCount = howManyDefinitionLines(lines);
		regexResolver = new RegexResolver(lines.subList(0, definitionLinesCount));
		
		states = Arrays.asList(lines.get(definitionLinesCount).substring(3).split(" "));
		tokenTypes = Arrays.asList(lines.get(definitionLinesCount + 1).substring(3).split(" ")).stream()
				.collect(Collectors.toSet());
		stateRulesMap = fillStateRulesMap(lines.subList(definitionLinesCount + 2, lines.size()), states);
		
		Utils.serializeObject("states.ser", states);
		Utils.serializeObject("token_types.ser", tokenTypes);
		Utils.serializeObject("state_rules_map.ser", stateRulesMap);
	}

    private static HashMap<String, List<Rule>> fillStateRulesMap(List<String> subList, List<String> states) {
        HashMap<String, List<Rule>> mapOfRules = new HashMap<>();
        states.forEach(e -> mapOfRules.put(e, new ArrayList<>()));

        for (int i = 0; i < subList.size(); i++) {
            String firstLine = subList.get(i);
            String[] splitted = firstLine.split(">", 2);
            Rule rule = new Rule();
            rule.setRegex(regexResolver.resolveRegexValue(splitted[1]));
            rule.setTokenType(subList.get(i + 2));
            i += 3;
            while (!subList.get(i).startsWith("}")) {
                rule.addCommand(subList.get(i++));
            }
            mapOfRules.get(splitted[0].substring(1)).add(rule);
        }
        return mapOfRules;
    }

    private static int howManyDefinitionLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("%X")) return i;
        }
        return -1;
    }

}
