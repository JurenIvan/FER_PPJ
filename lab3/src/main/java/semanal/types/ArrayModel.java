package semanal.types;

import semanal.Utils;

import java.util.Objects;

public class ArrayModel {
    private static int MAX_ELEMENTS = 1024;
    private Type elementType;
    private int numberOfElements;
    private boolean initializedByString; // for example "abc"

    public ArrayModel(NumberType numberType, int numberOfElements, boolean initializedByString) {
        this.elementType = Type.createNumber(numberType);
        this.numberOfElements = numberOfElements;
        this.initializedByString = initializedByString;
    }

    public static boolean isValidArraySize(String size) {
        try {
            return isValidArraySize(Integer.parseInt(size));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isValidArraySize(int size) {
        return size > 0 && size <= MAX_ELEMENTS;
    }

    public static boolean isValidCharArray(String string) {
        if (!Utils.isAscii(string)) {
            return false;
        }

        string = string.substring(1, string.length() - 1); // remove surrounding quotation marks
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '\\') {
                if (i == string.length() - 1) {
                    return false;
                }

                c = string.charAt(i + 1);
                if (!(c == 't' || c == 'n' || c == '\\' || c == '0' || c == '\'' || c == '\"')) {
                    return false;
                }

                i++;
            }
        }

        return true;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isInitializedByString() {
        return initializedByString;
    }

    public NumberType getNumberType() {
        return elementType.getNumber();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ArrayModel))
            return false;
        ArrayModel that = (ArrayModel) o;
        return getNumberType() == that.getNumberType();
    }

    @Override public int hashCode() {
        return Objects.hash(getNumberType());
    }

    public Type getElementType() {
        return elementType;
    }

    public boolean implicitConvertInto(ArrayModel other) {
        // only the following is permitted:
        //      niz(T) --> niz(const(T))

        if (getNumberType().getPrimitiveNumberType() != other.getNumberType().getPrimitiveNumberType())
            return false;

        return getNumberType().isNotConst();
    }

}
