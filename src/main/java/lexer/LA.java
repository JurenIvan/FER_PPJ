package lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class LA {

    private final List<String> states;
    private final Set<String> tokenTypes;
    private Map<String, MEA> stateToMEA;
    private final HashMap<String, List<Rule>> stateRulesMap;
    public static LA la;
    public Inputter inputter;

    public LA() {
        this.stateRulesMap = Utils.deserializeObject("state_rules_map.ser");
        this.tokenTypes = Utils.deserializeObject("token_types.ser");
        this.states = Utils.deserializeObject("states.ser");
        inputter = new Inputter();

        stateToMEA = stateRulesMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
                e -> new MEA(e.getValue().stream().map(Rule::getEnkaGraph).collect(Collectors.toList()))));
    }

    public static void main(String[] args) {
        System.out.print(new LA().run());
    }


    public String run() {
        String input = inputter.outputString();
        StringBuilder sb = new StringBuilder();

        String currentState = states.get(0);
        stateToMEA.get(currentState).reset();

        int lineCount = 1;
        for (int i = 0; i < input.length(); ) {
            MEA mea = stateToMEA.get(currentState);
            int lastI = i;

            int lastResult = -1;
            int lastAcceptableResult = -1;
            int lastAcceptableResultIndex = -1;
            int result = -1;
            while (i < input.length() && ((result = mea.parseNext(input.charAt(i++))) != -1 || lastResult == -2)) {
                lastResult = result;
                if (result >= 0) {
                    lastAcceptableResult = result;
                    lastAcceptableResultIndex = i;
                }
            }

            if (lastAcceptableResult == -1) {
                i = lastI + 1;
            } else {
                i = lastAcceptableResultIndex;
                Rule rule = stateRulesMap.get(currentState).get(lastAcceptableResult);
                int captureLineCount = lineCount;
                for (String command : rule.getCommands()) {
                    String[] parts = command.split(" ");
                    switch (parts[0]) {
                        case "NOVI_REDAK":
                            lineCount++;
                            break;
                        case "VRATI_SE":
                            i = lastI + Integer.valueOf(parts[1]);
                            break;
                        case "UDJI_U_STANJE":
                            if (!currentState.equals(parts[1])) {
                                stateToMEA.get(parts[1]).reset();
                            }
                            currentState = parts[1];
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown rule command --> " + parts[0]);
                    }
                }

                if (!"-".equals(rule.getTokenType())) {
                    String value = input.substring(lastI, i);
                    lastI = i;
                    sb.append(rule.getTokenType());
                    sb.append(" ");
                    sb.append(captureLineCount);
                    sb.append(" ");
                    sb.append(value);
                    sb.append("\n");
                }
            }
            mea.reset();
        }
        return sb.toString();
    }

    public void setInputter(Inputter inputter) {
        this.inputter = inputter;
    }
}
