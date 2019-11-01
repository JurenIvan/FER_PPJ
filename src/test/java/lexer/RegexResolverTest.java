package lexer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RegexResolverTest {

    private static final String INPUT1 = "{znamenka} 0|1|2|3|4|5|6|7|8|9\n" +
            "{hexZnamenka} {znamenka}|a|b|c|d|e|f|A|B|C|D|E|F\n" +
            "{broj} {znamenka}{znamenka}*|0x{hexZnamenka}{hexZnamenka}*\n" +
            "{bjelina} \\t|\\n|\\_\n" +
            "{sviZnakovi} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\n|\\_|!|\"|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~";

    private static final String OUT1 = "{znamenka} 0|1|2|3|4|5|6|7|8|9\n" +
            "{hexZnamenka} (0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F\n" +
            "{broj} (0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*|0x((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)*\n" +
            "{bjelina} \\t|\\n|\\_\n" +
            "{sviZnakovi} \\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\n|\\_|!|\"|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~";

    //todo tests
    @Test
    public void noBrackets_noBrackets_returnInputString() {
        RegexResolver regexResolver = new RegexResolver(Arrays.asList(INPUT1.split("\n")));
    }


}
