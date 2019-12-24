package semanal;

import java.nio.charset.Charset;

public class Utils {

    public static boolean isAscii(String s) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s);
    }

}
