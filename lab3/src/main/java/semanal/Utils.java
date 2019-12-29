package semanal;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static boolean isAscii(String s) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(s);
    }

    public static <E> List<E> listOf(E... es) {
        List<E> list = new ArrayList<>();
        list.addAll(Arrays.asList(es));
        return list;
    }
}
