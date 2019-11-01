package lexer;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    @Test
    public void test_Duljina() throws IOException {
        Path path = Path.of("data/lab1/tests/duljina");
        executeIntegrationTest(readFolder(path));
    }

    private void executeIntegrationTest(List<List<String>> data) throws IOException {
        GLA gla = new GLA();
        Mockito.when(gla.getInput()).thenReturn(data.get(0));

        gla.run();
        LA la = Mockito.mock(LA.class);
        Mockito.when(la.getInput()).thenReturn(String.valueOf(data.get(1).stream().reduce(String::concat)));

        Assertions.assertEquals(data.get(2).stream().reduce(String::concat), new LA().run());
    }

    private List<List<String>> readFolder(Path path) throws IOException {
        List<List<String>> sol = new ArrayList<>();
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.in")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.lan")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.out")));
        return sol;
    }


}
