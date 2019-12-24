package semanal.types;

import semanal.Utils;

import java.util.Objects;

public enum NumberType {

    INT(PrimitiveNumberType.INT, false), CHAR(PrimitiveNumberType.CHAR, false), CONST_INT(PrimitiveNumberType.INT, true), CONST_CHAR(
            PrimitiveNumberType.CHAR, true);

    private boolean isConst;
    private PrimitiveNumberType primitiveNumberType;

    private NumberType(PrimitiveNumberType primitiveNumberType, boolean isConst) {
        this.primitiveNumberType = Objects.requireNonNull(primitiveNumberType);
        this.isConst = isConst;
    }

    /**
     * Utility method that checks if the given string represents an integer in the range of (-2^31, 2^31 - 1) aka (-2147483648, 2147483647).
     *
     * @param s the string representing the integer to check
     * @return true if given string is a parsable integer
     */
    private static boolean checkIfInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidChar(String s) {
        if (!Utils.isAscii(s) || s.length() > 4) {
            return false;
        }

        if (s.length() == 3) {
            return true;
        }

        s = s.substring(1, 3); // remove surrounding quotation marks
        return "\\t".equals(s) || "\\n".equals(s) || "\\0".equals(s) || "\\'".equals(s) || "\\\"".equals(s) || "\\\\".equals(s);
    }

    public PrimitiveNumberType getPrimitiveNumberType() {
        return primitiveNumberType;
    }

    public boolean isConst() {
        return isConst;
    }

    public boolean checkIfValidValue(String value) {
        if (primitiveNumberType == PrimitiveNumberType.INT) {
            return checkIfInt(value);
        } else {
            return isValidChar(value);
        }
    }

    public boolean implicitConvertInto(NumberType other) {
        if (primitiveNumberType == PrimitiveNumberType.INT && other.primitiveNumberType == PrimitiveNumberType.CHAR) {
            return false;
        }
        return true;
    }

    public static boolean implicitConvertInto(Type type, NumberType other) {
        if (type.getSubType() != SubType.NUMBER) {
            return false;
        }

        return type.getNumber().implicitConvertInto(other);
    }

}
