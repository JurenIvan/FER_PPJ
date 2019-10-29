package lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class LA {

    private final List<String> states;
    private final Set<String> tokenTypes;
    private Map<String, MEA> stateToMEA;
    private final HashMap<String, List<Rule>> stateRulesMap;

	public LA(List<String> states, Set<String> tokenTypes, HashMap<String, List<Rule>> stateRulesMap) {
		this.stateRulesMap = stateRulesMap;
		this.tokenTypes = tokenTypes;
		this.states = states;

		stateToMEA = stateRulesMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
				e -> new MEA(e.getValue().stream().map(Rule::getEnkaGraph).collect(Collectors.toList()))));
	}
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			sb.append(sc.nextLine());
			if(sc.hasNext()) {
				sb.append("\n");
			}
		}
		sc.close();
		
		List<String> states = Utils.deserializeObject("states.ser");
		Set<String> tokenTypes = Utils.deserializeObject("token_types.ser");
		HashMap<String, List<Rule>> stateRulesMap = Utils.deserializeObject("state_rules_map.ser");

		LA la = new LA(states, tokenTypes, stateRulesMap);
		
		String result;
		if(args.length == 2) {
			result = la.run(sb.toString(), Boolean.parseBoolean(args[0]), Boolean.parseBoolean(args[1]));
		} else {
			result = la.run(sb.toString());
		}
		
		System.out.print(result);	
	}
	
	private String run(String input) {
		return run(input, false, false);
	}
	
	private String run(String input, boolean debug, boolean errorLinesDebug) {
		StringBuilder sb = new StringBuilder();

		String currentState = states.get(0);
		stateToMEA.get(currentState).reset();

		int lineCount = 1;
		for (int i = 0; i < input.length();) {
			MEA mea = stateToMEA.get(currentState);
			
			int lastI = i;
			int lastResult = -1;
			int lastAcceptableResult = -1;
			int lastAcceptableResultIndex = -1;
			int result = -1;
			if (debug) {
				System.out.println("BEFORE:\t" + currentState + " i:" + i + " lastI:" + lastI + " lineCount:"
						+ lineCount + " lastResult:" + lastAcceptableResult);
				System.out.println(input.substring(0, i) + "*" + input.substring(Math.min(i + 1, input.length())));
				System.out.println();
			}
			while (i < input.length() && (-1 != (result = mea.parseNext(input.charAt(i++))) || lastResult == -2)) {
				if(debug) {
					System.out.println("--> current result: " + result);
				}
				lastResult = result;
				if(result>=0) {
					lastAcceptableResult = result;
					lastAcceptableResultIndex = i;
				}
			}

			if (debug) {
				System.out.println("AFTER:\t" + currentState + " i:" + i + " lastI:" + lastI + " lineCount:" + lineCount
						+ " lastAcceptableResult:" + lastAcceptableResult + " result:" + result + " lastAcceptableResultIndex:" + lastAcceptableResultIndex);
				try{
					Rule rule = stateRulesMap.get(currentState).get(lastAcceptableResult);
					System.out.println("Rule: " + rule + " -----> " + rule.getRegex());
				} catch (Exception ignored) {
				}
				if(lastAcceptableResult != -1) {
					System.out.println(input.substring(0, lastI) + "*".repeat(lastAcceptableResultIndex - lastI)
							+ "x".repeat(i - lastAcceptableResultIndex) + input.substring(Math.min(i + 1, input.length())));
				}
				System.out.println();

			}

			if (lastAcceptableResult == -1) {
				i = lastI + 1;
				if (errorLinesDebug) {
					System.out.println("######### POKUSAVAM POPRAVITI POGRESKU #########");
				}
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

					if(debug) {
						System.out.print(rule.getTokenType() + " " + captureLineCount + " " + value + "\n");
					}
				}

			}

			if (debug) {
				System.out.println("BOTTOM:\t" + currentState + " i:" + i + " lastI:" + lastI + " lineCount:"
						+ lineCount + " lastResult:" + lastAcceptableResult);
				System.out.println();
				System.out.println();
				System.out.println();
			}

			mea.reset();
		}

		return sb.toString();
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
