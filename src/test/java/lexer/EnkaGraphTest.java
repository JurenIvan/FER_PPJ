package lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("javadoc")
class EnkaGraphTest {

    @Test
    void testRegex() {
        String regex = "a|b|c(a|b*|c)(a|b*|(c|d))*|d";

        runTest(new StringTest("a", true), regex);
        runTest(new StringTest("b", true), regex);
        runTest(new StringTest("c", true), regex);
        runTest(new StringTest("d", true), regex);
        runTest(new StringTest("cc", true), regex);
        runTest(new StringTest("ca", true), regex);
        runTest(new StringTest("cb", true), regex);
        runTest(new StringTest("cd", true), regex);
        runTest(new StringTest("cdcc", true), regex);
        runTest(new StringTest("e", false), regex);
        runTest(new StringTest(" ", false), regex);
        runTest(new StringTest(" a", false), regex);
        runTest(new StringTest("ce", false), regex);
        runTest(new StringTest("aa", false), regex);
        runTest(new StringTest("bb", false), regex);
        runTest(new StringTest("accc", false), regex);
        runTest(new StringTest("dd", false), regex);

    }

    @Test
    void testSimpleRegex() {
        String regex = "aaaa|aaaab|abaaaac|(((c)))|(d|ee*)|f";

        runTest(new StringTest("a", false), regex);
        runTest(new StringTest("aa", false), regex);
        runTest(new StringTest("aaa", false), regex);
        runTest(new StringTest("aaaa", true), regex);
        runTest(new StringTest("aaaaa", false), regex);
        runTest(new StringTest("b", false), regex);
        runTest(new StringTest("bb", false), regex);
        runTest(new StringTest("bbb", false), regex);
        runTest(new StringTest("bbbb", false), regex);
        runTest(new StringTest("bbbbb", false), regex);
        runTest(new StringTest("c", true), regex);
        runTest(new StringTest("cc", false), regex);
        runTest(new StringTest("ccc", false), regex);
        runTest(new StringTest("cccc", false), regex);
        runTest(new StringTest("ccccc", false), regex);
        runTest(new StringTest("d", true), regex);
        runTest(new StringTest("dd", false), regex);
        runTest(new StringTest("ddd", false), regex);
        runTest(new StringTest("dddd", false), regex);
        runTest(new StringTest("ddddd", false), regex);
        runTest(new StringTest("e", true), regex);
        runTest(new StringTest("ee", true), regex);
        runTest(new StringTest("eee", true), regex);
        runTest(new StringTest("eeee", true), regex);
        runTest(new StringTest("eeeee", true), regex);

        runTest(new StringTest("aaaab", true), regex);
        runTest(new StringTest("ea", false), regex);

    }


    private void runTest(StringTest test, String regex) {
        MEA mea = new MEA(List.of(new EnkaGraph(regex)));

        System.out.println(test);

        mea.reset();
        int last = -1;
        for (char c : test.value.toCharArray()) {
            last = mea.parseNext(c);
        }
        assertEquals(test.acceptable, last != -1);
    }

    private void runTest2(StringTest test, String regex) {
        EnkaAutomata auto = new EnkaAutomata(new EnkaGraph(regex));

//		System.out.println(test);
//		System.out.println(" --> " + graph.simulate(test.value));
        auto.reset();
        for (char c : test.value.toCharArray()) {
            auto.doTransitions(c);
        }
        assertEquals(test.acceptable, auto.isAnyStateAcceptable());
    }

    private static class StringTest {
        String value;
        boolean acceptable;

        public StringTest(String test, boolean acceptable) {
            super();
            this.value = test;
            this.acceptable = acceptable;
        }

        @Override
        public String toString() {
            return value + "-->" + acceptable;
        }
    }

}
