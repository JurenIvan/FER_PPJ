package lexer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTest {

    private static String concatStringsWithNewLine(String s, String s2) {
        return s + '\n' + s2;
    }

    @Test
    public void test_minusLang() throws IOException {
        Path path = Path.of("data/lab1/tests/minusLang");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_minusLang_laksi() throws IOException {
        Path path = Path.of("data/lab1/tests/minusLang_laksi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_minusLang_tezi() throws IOException {
        Path path = Path.of("data/lab1/tests/minusLang_tezi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_nadji_a1() throws IOException {
        Path path = Path.of("data/lab1/tests/nadji_a1");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_nadji_a2() throws IOException {
        Path path = Path.of("data/lab1/tests/nadji_a2");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_nadji_x() throws IOException {
        Path path = Path.of("data/lab1/tests/nadji_x");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_nadji_x_oporavak() throws IOException {
        Path path = Path.of("data/lab1/tests/nadji_x_oporavak");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_nadji_x_retci() throws IOException {
        Path path = Path.of("data/lab1/tests/nadji_x_retci");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_poredak() throws IOException {
        Path path = Path.of("data/lab1/tests/poredak");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_ppjLang_laksi() throws IOException {
        Path path = Path.of("data/lab1/tests/ppjLang_laksi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_ppjLang_tezi() throws IOException {
        Path path = Path.of("data/lab1/tests/ppjLang_tezi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_regex_escapes() throws IOException {
        Path path = Path.of("data/lab1/tests/regex_escapes");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_regex_laksi() throws IOException {
        Path path = Path.of("data/lab1/tests/regex_laksi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_regex_regdefs() throws IOException {
        Path path = Path.of("data/lab1/tests/regex_regdefs");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_regex_tezi() throws IOException {
        Path path = Path.of("data/lab1/tests/regex_tezi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_simplePpjLang() throws IOException {
        Path path = Path.of("data/lab1/tests/simplePpjLang");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_simplePpjLang_laksi() throws IOException {
        Path path = Path.of("data/lab1/tests/simplePpjLang_laksi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_simplePpjLang_tezi() throws IOException {
        Path path = Path.of("data/lab1/tests/simplePpjLang_tezi");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_state_hopper() throws IOException {
        Path path = Path.of("data/lab1/tests/state_hopper");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_svaki_drugi_a1() throws IOException {
        Path path = Path.of("data/lab1/tests/svaki_drugi_a1");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_svaki_drugi_a2() throws IOException {
        Path path = Path.of("data/lab1/tests/svaki_drugi_a2");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_svaki_treci_x() throws IOException {
        Path path = Path.of("data/lab1/tests/svaki_treci_x");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_vrati_se() throws IOException {
        Path path = Path.of("data/lab1/tests/vrati_se");
        executeIntegrationTest(readFolder(path));
    }

    @Test
    public void test_vrati_se_prioritet() throws IOException {
        Path path = Path.of("data/lab1/tests/vrati_se_prioritet");
        executeIntegrationTest(readFolder(path));
    }


    private void executeIntegrationTest(List<List<String>> data) throws IOException {
        Inputter inputter = Mockito.mock(Inputter.class);
        Mockito.when(inputter.outputListString()).thenReturn(data.get(0));
        Mockito.when(inputter.outputString()).thenReturn(data.get(1).stream().reduce(((s, s2) -> s + '\n' + s2)).get());

        GLA gla = new GLA();
        gla.setInputter(inputter);
        gla.run();

        LA la = new LA();
        la.setInputter(inputter);

        Assertions.assertEquals(data.get(2).stream().reduce(IntegrationTest::concatStringsWithNewLine).get() + '\n', la.run());
    }

    private List<List<String>> readFolder(Path path) throws IOException {
        List<List<String>> sol = new ArrayList<>();
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.lan")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.in")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.out")));
        return sol;
    }
}
