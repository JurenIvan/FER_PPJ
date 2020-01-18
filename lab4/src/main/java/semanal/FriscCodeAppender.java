package semanal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class FriscCodeAppender {

    private static FriscCodeAppender friscCodeAppender;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LABEL_LENGTH = 5;
    private static final Random random = new Random();

    private static final String initBlock =
            "    MOVE 40000, R7\n" +
                    "    CALL main\n";

    private StringBuilder init = new StringBuilder();
    private StringBuilder main = new StringBuilder();

    private FriscCodeAppender() {
    }

    public static FriscCodeAppender getFriscCodeAppender() {
        if (friscCodeAppender == null)
            friscCodeAppender = new FriscCodeAppender();
        return friscCodeAppender;
    }

    /**
     * Returns label to value
     *
     * @param value to be saved
     * @return labes
     */
    public String appendConstant(String value) {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < LABEL_LENGTH; i++)
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));

        String label = sb.toString();
        init.append(label).append(" \t ").append(value).append("\n");
        return label;
    }

    public void appendCommand(String command) {
        main.append("\t\t").append(command).append("\n");
    }

    public void appendCommand(String label, String command) {
        main.append(label).append(" \t ").append(command).append("\n");
    }


    public String toStringInit() {
        return init.toString();
    }

    public String toStringMain() {
        return main.toString();
    }

    public String getCode() {
        return new StringBuilder()
                .append(initBlock).append("\n")
                .append(toStringInit()).append("\n")
                .append(toStringMain()).append("\n")
                .toString();
    }

    public void writeToFile(String filename) {
        try {
            Files.writeString(Paths.get(filename), getCode(), UTF_8, CREATE_NEW);
        } catch (IOException e) {
            System.out.println("Failed to write into directory");
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public void reset() {
        init = new StringBuilder();
        main = new StringBuilder();
    }
}
