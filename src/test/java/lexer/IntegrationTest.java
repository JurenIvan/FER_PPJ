package lexer;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTest {

    private List<List<String>> readFolder(Path path) throws IOException {
        List<List<String>> sol = new ArrayList<>();
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.in")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.lan")));
        sol.add(Files.readAllLines(Path.of(path.toString(), "test.out")));
        return sol;
    }

    @Test
    public void test_Duljina() throws IOException {
        Path path=Path.of("data/lab1/tests/duljina");
        var sol=readFolder(path);


    }



}
