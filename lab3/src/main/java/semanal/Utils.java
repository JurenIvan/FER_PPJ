package semanal;

import java.nio.charset.Charset;

public class Utils {
    public static boolean isValidChar(String s) {
        if (!isAscii(s) || s.length() > 4) {
            return false;
        }

        if (s.length() == 3) {
            return true;
        }

        s = s.substring(1, 3); // remove surrounding quotation marks
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

    public static boolean isValidCharArray(String string) {
        if (!isAscii(string)) {
            return false;
        }

        string = string.substring(1, string.length() - 1); // remove surrounding quotation marks
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '\\') {
                if (i == string.length() - 1) {
                    return false;
                }

                if (!(c == 't' || c == 'n' || c == '\\' || c == '0' || c == '\'' || c == '\"')) {
                    return false;
                }

                i++;
            }
        }

        return true;
    }
}
