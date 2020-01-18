package semanal;

import java.io.*;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FriscCodeAppender {

    private static FriscCodeAppender friscCodeAppender;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LABEL_LENGTH = 6;
    private static final Random random = new Random();

    private static final String initBlock =
            "    MOVE 40000, R7\n" +
                    "    CALL main\n" +
                    "    HALT\n" +
                    "main POP R4"; // TODO Privremeno

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
    public String appendConstant(int value) {
        String label = generateLabel();
        init.append(label).append(" \t DW %D ").append(value).append("\n");
        return label;
    }

    public String generateLabel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LABEL_LENGTH; i++)
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));

        return sb.toString();
    }

    public String appendConstant(int value, String name) {
        init.append(name).append(" \t DW %D ").append(value).append("\n");
        return name;
    }

    public void appendCommand(String command) {
        main.append("\t\t").append(command).append("\n");
    }

    public void appendLabel(String label) {
        main.append(label).append("\n");
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
                .append(toStringMain()).append("\n")
                .append(toStringInit()).append("\n")
                .toString();
    }

    public void writeToFile(String filename) {
        //System.out.println(getCode());
        Writer writter = null;
        try {
            writter = (new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), UTF_8)));
            writter.write(getCode());
            writter.close();
            //   Files.writeString(Paths.get(filename), getCode(), UTF_8, CREATE_NEW);
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
