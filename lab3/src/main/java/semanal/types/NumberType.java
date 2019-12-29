package semanal.types;

import semanal.Utils;

import java.util.Objects;

public enum NumberType {

    INT(PrimitiveNumberType.INT, false), CHAR(PrimitiveNumberType.CHAR, false), CONST_INT(PrimitiveNumberType.INT, true), CONST_CHAR(
            PrimitiveNumberType.CHAR, true);

    private boolean isConst;
    private PrimitiveNumberType primitiveNumberType;

    NumberType(PrimitiveNumberType primitiveNumberType, boolean isConst) {
        this.primitiveNumberType = Objects.requireNonNull(primitiveNumberType);
        this.isConst = isConst;
    }

    /**
     * Utility method that checks if the given string represents an decimal or hexadecimal integer in the
     * range of (-2^31, 2^31 - 1) aka (-2147483648, 2147483647).
     *
     * @param s the string representing the integer to check in decimal (123) or hexadecimal format (0x123)
     * @return true if given string is a parsable integer
     */
    private static boolean checkIfInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ignored) {
        }

        try {
            if (s.startsWith("0x")) {
                Integer.parseInt(s.substring(2), 16);
                return true;
            }
        } catch (NumberFormatException ignored) {
        }

        return false;
    }

    private static boolean isValidChar(String s) {
        if (!Utils.isAscii(s) || s.length() > 4 || s.length() < 3) {
            return false;
        }

        if (s.length() == 3) {
            return s.charAt(1) != '\\';
        }

        s = s.substring(1, 3); // remove surrounding quotation marks
        return "\\t".equals(s) || "\\n".equals(s) || "\\0".equals(s) || "\\'".equals(s) || "\\\"".equals(s) || "\\\\".equals(s);
    }

    public static boolean implicitConvertInto(Type type, NumberType other) {
        if (type.getSubType() != SubType.NUMBER) {
            return false;
        }

        return type.getNumber().implicitConvertInto(other);
    }

    public PrimitiveNumberType getPrimitiveNumberType() {
        return primitiveNumberType;
    }

    public boolean isNotConst() {
        return !isConst;
    }

    public NumberType toConst() {
        if (isConst)
            return this;

        if (this == INT)
            return CONST_INT;

        return CONST_CHAR;
    }

    public NumberType toNonConst() {
        if (!isConst)
            return this;

        if (this == CONST_INT)
            return INT;

        return CHAR;
    }

    public boolean checkIfValidValue(String value) {
        if (primitiveNumberType == PrimitiveNumberType.INT) {
            return checkIfInt(value);
        } else {
            return isValidChar(value);
        }
    }

    public boolean implicitConvertInto(NumberType other) {
        return other != null && (primitiveNumberType != PrimitiveNumberType.INT || other.primitiveNumberType != PrimitiveNumberType.CHAR);
    }
}
