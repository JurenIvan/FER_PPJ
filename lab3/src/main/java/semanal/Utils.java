package semanal;

import java.nio.charset.Charset;

public class Utils {
    public static boolean isValidChar(String s) {
        if (!isAscii(s)) {
            return false;
        }
        return "\\t".equals(s) || "\\n".equals(s) || "\\0".equals(s) || "\\'".equals(s) || "\\\"".equals(s) || "\\\\".equals(s);
    }

    private static boolean isAscii(String s) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s);
    }

    public static boolean checkIfInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
