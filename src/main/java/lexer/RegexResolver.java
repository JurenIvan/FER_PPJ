package lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RegexResolver {

    HashMap<String, String> definitions = new HashMap<>();

    public RegexResolver(List<String> inputLines) {
        Set<String> keys = fillHashSet(inputLines);
        for (String key : keys) {
            resolveRegex(key);
        }
    }

    public HashMap<String, String> getDefinitions() {
        return definitions;
    }

    public Set<String> fillHashSet(List<String> inputLine) {
        for (String line : inputLine) {
            String[] splitted = line.split(" ");
            definitions.put(splitted[0], splitted[1]);
        }
        return definitions.keySet();
    }

    //todo tests
    private String resolveRegex(String key) {
        String value = definitions.get(key);
        List<String> unresolved = returnForeignDependenciesFromProduction(value);
        for (String toReplace : unresolved) {
            value = value.replace(toReplace, String.format("(%s)", resolveRegex(toReplace)));
            definitions.put(key, value);
        }
        return value;
    }

    public String resolveRegexValue(String value) {
        List<String> unresolved = returnForeignDependenciesFromProduction(value);
        for (String toReplace : unresolved) {
            value = value.replace(toReplace, String.format("(%s)", resolveRegex(toReplace)));
        }
        return value;
    }

    //todo tests
    private List<String> returnForeignDependenciesFromProduction(String regex) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < regex.length(); i++) {
            if (regex.charAt(i) == '\\') {
                i++;
                continue;
            }
            if (regex.charAt(i) == '{') {
                while (regex.charAt(i) != '}') sb.append(regex.charAt(i++));
                result.add(sb.append('}').toString());
                sb.setLength(0);
            }
        }
        return result;
    }
}
