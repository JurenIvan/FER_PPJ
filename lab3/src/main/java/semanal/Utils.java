package semanal;

import java.nio.charset.StandardCharsets;

public class Utils {

    public static boolean isAscii(String s) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(s);
    }
}
