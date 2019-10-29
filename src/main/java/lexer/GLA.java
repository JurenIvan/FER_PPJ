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
//		Scanner sc = new Scanner(input);
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

//            if("'{sveOsimJednostrukogNavodnikaNovogRedaITaba}'".equals(splitted[1])) {
//				try {
//					GraphUtils.runGraph(rule.getEnkaGraph());
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} // TODO remove
//            }
        }
        return mapOfRules;
    }

    private static int howManyDefinitionLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("%X")) return i;
        }
        return -1;
    }


    private static final String input = "{znak} a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z\n" +
            "{znamenka} 0|1|2|3|4|5|6|7|8|9\n" +
            "{hexZnamenka} {znamenka}|a|b|c|d|e|f|A|B|C|D|E|F\n" +
            "{bjelina} \\t|\\n|\\_\n" +
            "{eksponent} (e|E)($|+|-){znamenka}{znamenka}*\n" +
            "{sviZnakovi} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\n|\\_|!|\"|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~\n" +
            "{sveOsimDvostrukogNavodnikaINovogReda} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\_|!|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~\n" +
            "{sveOsimJednostrukogNavodnikaNovogRedaITaba} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\_|!|\"|#|%|&|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~\n" +
            "{sveOsimNovogRedaITaba} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\_|!|\"|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~\n" +
            "%X S_pocetno S_komentar S_jednolinijskiKomentar S_string\n" +
            "%L IDN BROJ ZNAK NIZ_ZNAKOVA KR_BREAK KR_CHAR KR_CONST KR_CONTINUE KR_ELSE KR_FLOAT KR_FOR KR_IF KR_INT KR_RETURN KR_STRUCT KR_VOID KR_WHILE PLUS OP_INC MINUS OP_DEC ASTERISK OP_DIJELI OP_MOD OP_PRIDRUZI OP_LT OP_LTE OP_GT OP_GTE OP_EQ OP_NEQ OP_NEG OP_TILDA OP_I OP_ILI AMPERSAND OP_BIN_ILI OP_BIN_XILI ZAREZ TOCKAZAREZ TOCKA L_ZAGRADA D_ZAGRADA L_UGL_ZAGRADA D_UGL_ZAGRADA L_VIT_ZAGRADA D_VIT_ZAGRADA\n" +
            "<S_pocetno>\\t|\\_\n" +
            "{\n" +
            "-\n" +
            "}\n" +
            "<S_pocetno>\\n\n" +
            "{\n" +
            "-\n" +
            "NOVI_REDAK\n" +
            "}\n" +
            "<S_pocetno>//\n" +
            "{\n" +
            "-\n" +
            "UDJI_U_STANJE S_jednolinijskiKomentar\n" +
            "}\n" +
            "<S_jednolinijskiKomentar>\\n\n" +
            "{\n" +
            "-\n" +
            "NOVI_REDAK\n" +
            "UDJI_U_STANJE S_pocetno\n" +
            "}\n" +
            "<S_jednolinijskiKomentar>{sviZnakovi}\n" +
            "{\n" +
            "-\n" +
            "}\n" +
            "<S_pocetno>/\\*\n" +
            "{\n" +
            "-\n" +
            "UDJI_U_STANJE S_komentar\n" +
            "}\n" +
            "<S_komentar>\\*/\n" +
            "{\n" +
            "-\n" +
            "UDJI_U_STANJE S_pocetno\n" +
            "}\n" +
            "<S_komentar>\\n\n" +
            "{\n" +
            "-\n" +
            "NOVI_REDAK\n" +
            "}\n" +
            "<S_komentar>{sviZnakovi}\n" +
            "{\n" +
            "-\n" +
            "}\n" +
            "<S_pocetno>\"\n" +
            "{\n" +
            "-\n" +
            "UDJI_U_STANJE S_string\n" +
            "VRATI_SE 0\n" +
            "}\n" +
            "<S_string>\"({sveOsimDvostrukogNavodnikaINovogReda}|\\\\\")*\"\n" +
            "{\n" +
            "NIZ_ZNAKOVA\n" +
            "UDJI_U_STANJE S_pocetno\n" +
            "}\n" +
            "<S_pocetno>break\n" +
            "{\n" +
            "KR_BREAK\n" +
            "}\n" +
            "<S_pocetno>char\n" +
            "{\n" +
            "KR_CHAR\n" +
            "}\n" +
            "<S_pocetno>const\n" +
            "{\n" +
            "KR_CONST\n" +
            "}\n" +
            "<S_pocetno>continue\n" +
            "{\n" +
            "KR_CONTINUE\n" +
            "}\n" +
            "<S_pocetno>else\n" +
            "{\n" +
            "KR_ELSE\n" +
            "}\n" +
            "<S_pocetno>float\n" +
            "{\n" +
            "KR_FLOAT\n" +
            "}\n" +
            "<S_pocetno>for\n" +
            "{\n" +
            "KR_FOR\n" +
            "}\n" +
            "<S_pocetno>if\n" +
            "{\n" +
            "KR_IF\n" +
            "}\n" +
            "<S_pocetno>int\n" +
            "{\n" +
            "KR_INT\n" +
            "}\n" +
            "<S_pocetno>return\n" +
            "{\n" +
            "KR_RETURN\n" +
            "}\n" +
            "<S_pocetno>struct\n" +
            "{\n" +
            "KR_STRUCT\n" +
            "}\n" +
            "<S_pocetno>void\n" +
            "{\n" +
            "KR_VOID\n" +
            "}\n" +
            "<S_pocetno>while\n" +
            "{\n" +
            "KR_WHILE\n" +
            "}\n" +
            "<S_pocetno>(_|{znak})(_|{znak}|{znamenka})*\n" +
            "{\n" +
            "IDN\n" +
            "}\n" +
            "<S_pocetno>{znamenka}{znamenka}*\n" +
            "{\n" +
            "BROJ\n" +
            "}\n" +
            "<S_pocetno>0(X|x){hexZnamenka}{hexZnamenka}*\n" +
            "{\n" +
            "BROJ\n" +
            "}\n" +
            "<S_pocetno>{znamenka}{znamenka}*.{znamenka}*($|{eksponent})\n" +
            "{\n" +
            "BROJ\n" +
            "}\n" +
            "<S_pocetno>{znamenka}*.{znamenka}{znamenka}*($|{eksponent})\n" +
            "{\n" +
            "BROJ\n" +
            "}\n" +
            "<S_pocetno>'{sveOsimJednostrukogNavodnikaNovogRedaITaba}'\n" +
            "{\n" +
            "ZNAK\n" +
            "}\n" +
            "<S_pocetno>'\\\\{sveOsimNovogRedaITaba}'\n" +
            "{\n" +
            "ZNAK\n" +
            "}\n" +
            "<S_pocetno>++\n" +
            "{\n" +
            "OP_INC\n" +
            "}\n" +
            "<S_pocetno>--\n" +
            "{\n" +
            "OP_DEC\n" +
            "}\n" +
            "<S_pocetno>+\n" +
            "{\n" +
            "PLUS\n" +
            "}\n" +
            "<S_pocetno>-\n" +
            "{\n" +
            "MINUS\n" +
            "}\n" +
            "<S_pocetno>\\*\n" +
            "{\n" +
            "ASTERISK\n" +
            "}\n" +
            "<S_pocetno>/\n" +
            "{\n" +
            "OP_DIJELI\n" +
            "}\n" +
            "<S_pocetno>%\n" +
            "{\n" +
            "OP_MOD\n" +
            "}\n" +
            "<S_pocetno>=\n" +
            "{\n" +
            "OP_PRIDRUZI\n" +
            "}\n" +
            "<S_pocetno><\n" +
            "{\n" +
            "OP_LT\n" +
            "}\n" +
            "<S_pocetno><=\n" +
            "{\n" +
            "OP_LTE\n" +
            "}\n" +
            "<S_pocetno>>\n" +
            "{\n" +
            "OP_GT\n" +
            "}\n" +
            "<S_pocetno>>=\n" +
            "{\n" +
            "OP_GTE\n" +
            "}\n" +
            "<S_pocetno>==\n" +
            "{\n" +
            "OP_EQ\n" +
            "}\n" +
            "<S_pocetno>!=\n" +
            "{\n" +
            "OP_NEQ\n" +
            "}\n" +
            "<S_pocetno>!\n" +
            "{\n" +
            "OP_NEG\n" +
            "}\n" +
            "<S_pocetno>~\n" +
            "{\n" +
            "OP_TILDA\n" +
            "}\n" +
            "<S_pocetno>&&\n" +
            "{\n" +
            "OP_I\n" +
            "}\n" +
            "<S_pocetno>\\|\\|\n" +
            "{\n" +
            "OP_ILI\n" +
            "}\n" +
            "<S_pocetno>&\n" +
            "{\n" +
            "AMPERSAND\n" +
            "}\n" +
            "<S_pocetno>\\|\n" +
            "{\n" +
            "OP_BIN_ILI\n" +
            "}\n" +
            "<S_pocetno>^\n" +
            "{\n" +
            "OP_BIN_XILI\n" +
            "}\n" +
            "<S_pocetno>,\n" +
            "{\n" +
            "ZAREZ\n" +
            "}\n" +
            "<S_pocetno>;\n" +
            "{\n" +
            "TOCKAZAREZ\n" +
            "}\n" +
            "<S_pocetno>.\n" +
            "{\n" +
            "TOCKA\n" +
            "}\n" +
            "<S_pocetno>\\(\n" +
            "{\n" +
            "L_ZAGRADA\n" +
            "}\n" +
            "<S_pocetno>\\)\n" +
            "{\n" +
            "D_ZAGRADA\n" +
            "}\n" +
            "<S_pocetno>\\{\n" +
            "{\n" +
            "L_VIT_ZAGRADA\n" +
            "}\n" +
            "<S_pocetno>\\}\n" +
            "{\n" +
            "D_VIT_ZAGRADA\n" +
            "}\n" +
            "<S_pocetno>[\n" +
            "{\n" +
            "L_UGL_ZAGRADA\n" +
            "}\n" +
            "<S_pocetno>]\n" +
            "{\n" +
            "D_UGL_ZAGRADA\n" +
            "}";


}
