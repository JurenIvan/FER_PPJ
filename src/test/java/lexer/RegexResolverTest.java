package lexer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RegexResolverTest {

    private static final String INPUT1 = "{znamenka} 0|1|2|3|4|5|6|7|8|9\n" +
            "{hexZnamenka} {znamenka}|a|b|c|d|e|f|A|B|C|D|E|F\n";

    private static final String OUT1 = "{znamenka} 0|1|2|3|4|5|6|7|8|9\n" +
            "{hexZnamenka} (0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F\n";
    @Test
    public void noBrackets_noBrackets_returnInputString() {
        RegexResolver regexResolver = new RegexResolver(Arrays.asList(INPUT1.split("\n")));
        RegexResolver regexResolverOut = new RegexResolver(Arrays.asList(OUT1.split("\n")));

        Assertions.assertEquals(regexResolver.getDefinitions(),regexResolverOut.getDefinitions());
    }


}
