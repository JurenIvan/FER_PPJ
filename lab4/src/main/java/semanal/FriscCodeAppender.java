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
            "\t\tMOVE 40000, R7\n" +
                    "\t\tMOVE HEAP, R5\n" +
                    "\t\tCALL init\n" +
                    "\t\tCALL main\n" +
                    "\t\tHALT\n";

    private StringBuilder init = new StringBuilder();
    private StringBuilder main = new StringBuilder();
    private StringBuilder labels = new StringBuilder();

    private FriscCodeAppender() {
    }

    public static FriscCodeAppender getFriscCodeAppender() {
        if (friscCodeAppender == null)
            friscCodeAppender = new FriscCodeAppender();
        return friscCodeAppender;
    }

    public String appendConstant(int value) {
        String label = generateLabel();
        labels.append(label).append("\tDW %D ").append(value).append("\n");
        return label;
    }

    public String appendConstant(int value, String name) {
        labels.append(name).append("\tDW %D ").append(value).append("\n");
        return name;
    }

    public void appendLabelToMain(String label) {
        main.append(label).append("\n");
    }

    public void append(String command, WhereTo whereTo) {
        switch (whereTo) {
            case INIT:
                init.append("\t\t").append(command).append("\n");
                break;
            case MAIN:
                main.append("\t\t").append(command).append("\n");
                break;
        }
    }

    public void append(String label, String command, WhereTo whereTo) {
        switch (whereTo) {
            case INIT:
                init.append(label).append("\t").append(command).append("\n");
                break;
            case MAIN:
                main.append(label).append("\t").append(command).append("\n");
                break;
        }
    }

    private String toStringLabels() {
        return labels.toString();
    }

    private String toStringMain() {
        return main.toString();
    }

    private String toStringInit() {
        return init.toString();
    }

    public String getCode() {
        return new StringBuilder()
                .append(initBlock).append("\n")
                .append("init").append("\n").append(toStringInit()).append("\t\tRET\n").append("\n")
                .append(toStringMain()).append("\n")
                .append(toStringLabels()).append("\n")
                .append("HEAP").append("\n")
                .toString();
    }

    public void writeToFile(String filename) {
        try {
            Writer writter = (new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), UTF_8)));
            writter.write(getCode());
            writter.close();
        } catch (IOException e) {
            System.out.println("Failed to write into directory");
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public static String generateLabel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LABEL_LENGTH; i++)
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));

        return sb.toString();
    }
}
