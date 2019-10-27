package lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LA {

    private final List<String> states;
    private final Set<String> tokenTypes;
    private Map<String, MEA> stateToMEA;
    private final HashMap<String, List<Rule>> stateRulesMap;


    public static void fakeMain(HashMap<String, List<Rule>> stateRulesMap, Set<String> tokenTypes, List<String> states) {
        LA la = new LA(stateRulesMap, tokenTypes, states);
        System.out.println(la.run(input));
    }

    private String run(String input) {
        StringBuilder sb = new StringBuilder();

        String currentState = states.get(0);
        stateToMEA.get(currentState).reset();

        int lineCount = 1;
        int lastResult = -1;
        int result;
        int lastI = 0;
        for (int i = 0; i < input.length(); i++) {
            MEA mea = stateToMEA.get(currentState);
            while (-1 != (result = mea.parseNext(input.charAt(i++)))) lastResult = result;
            if (lastResult == -1) {

            } else {
                Rule rule = stateRulesMap.get(currentState).get(lastResult);
                int captureLineCount = lineCount;
                for (String command : rule.getCommands()) {
                    String[] parts = command.split(" ");
                    switch (parts[0]) {
                        case "NOVI_REDAK":
                            lineCount++;
                            break;
                        case "VRATI_SE":
                            i -= Integer.valueOf(parts[1]); // TODO
                            break;
                        case "UDJI_U_STANJE":
                            if (!currentState.equals(parts[1])) {
                                stateToMEA.get(parts[1]).reset();
                            }
                            currentState = parts[1];
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown rule command");
                    }
                }


                if (!"-".equals(rule.getTokenType())) {
                    String value = "value";
                    lastI = i; // TODO

                    sb.append(rule.getTokenType());
                    sb.append(" ");
                    sb.append(captureLineCount);
                    sb.append(" ");
                    sb.append(value);
                    sb.append("\n");
                    
                    System.out.println(sb.toString());
                    sb.setLength(0);
                }

            }

            mea.reset();
        }

        return sb.toString();
    }

    public LA(HashMap<String, List<Rule>> stateRulesMap, Set<String> tokenTypes, List<String> states) {
        this.stateRulesMap = stateRulesMap;
        this.tokenTypes = tokenTypes;
        this.states = states;

        stateToMEA = stateRulesMap
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(e -> e.getKey(),
                                e -> new MEA(e.getValue()
                                        .stream()
                                        .map(Rule::getEnkaGraph)
                                        .collect(Collectors.toList())
                                )
                        )
                );
    }

    private static final String input = "struct S {\n" +
            "    char t;\n" +
            "    int x;\n" +
            "};\n" +
            "\n" +
            "/*\n" +
            "ne radi nista\n" +
            "*/\n" +
            "void fun(int xYz) {\n" +
            "    return;\n" +
            "}\n" +
            "\n" +
            "// glavni program \"testira osnovne kljucne rijeci i operatore za lekser\"\n" +
            "int main(void) {\n" +
            "    int A[512];\n" +
            "    int t[] = {1,2,3};\n" +
            "    char tmp[] = \"te\\nst\";\n" +
            "    const char *x = \"\\\"tes\\\"t2\\\"\";\n" +
            "    int xYz, *abc;\n" +
            "    float a_B1=10.23 + .12 + 1.43e-3 + .43E3 + .46E+134 + 0.47E-123;\n" +
            "    int i;\n" +
            "    struct S strct;\n" +
            "    strct.t = 'b';\n" +
            "    strct.x = 4321;\n" +
            "    \n" +
            "    xYz = 12345; // nekakav komentar\n" +
            "    abc = &xYz;\n" +
            "    abc = (&xYz);\n" +
            "    *abc = *abc+++xYz;\n" +
            "    *abc = 054 % 5;\n" +
            "    *abc = 0xaafff;\n" +
            "    i = 3*2+5-3|3&3^3;\n" +
            "    \n" +
            "    tmp[1] = 'b';\n" +
            "    tmp[2] = '\\n';\n" +
            "    tmp[3] = '''; // greska\n" +
            "    tmp[0] = '\\'';\n" +
            "    for (i=0; i<4; ++i) {\n" +
            "        tmp[i] = (char)*abc; /* komentar\n" +
            "                                komentar\n" +
            "                                komentar */\n" +
            "        break;\n" +
            "        continue;\n" +
            "        return *&xYz;\n" +
            "    }\n" +
            "    \n" +
            "    if (1>=3 && i>2 || i<=12) {\n" +
            "        fun(3);\n" +
            "    } else {\n" +
            "        fun(5);\n" +
            "    }\n" +
            "    \n" +
            "    while (1) {\n" +
            "        break;\n" +
            "    }\n" +
            "    \n" +
            "    return 0;\n" +
            "}\n";
}
