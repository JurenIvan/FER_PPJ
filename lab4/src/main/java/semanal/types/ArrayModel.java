package semanal.types;

import semanal.Utils;

import java.util.Objects;

public class ArrayModel {
    private static int MAX_ELEMENTS = 1024;
    private Type elementType;
    private int numberOfElements;
    private boolean initializedByString; // for example "abc"
    private boolean copiedFromIDN;

    public ArrayModel(NumberType numberType, int numberOfElements, boolean initializedByString, boolean copiedFromIDN) {
        this.elementType = Type.createNumber(numberType);
        this.numberOfElements = numberOfElements;
        this.initializedByString = initializedByString;
        this.copiedFromIDN = copiedFromIDN;
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
        return getNumberOfCharElementsOfString(string) != -1;
    }

    public static int getNumberOfCharElementsOfString(String string) {
        if (!Utils.isAscii(string)) {
            return -1;
        }

        string = string.substring(1, string.length() - 1); // remove surrounding quotation marks
        int charCount = 0;
        for (int i = 0; i < string.length(); i++, charCount++) {
            char c = string.charAt(i);
            if (c == '\\') {
                if (i == string.length() - 1) {
                    return -1;
                }

                c = string.charAt(i + 1);
                if (!(c == 't' || c == 'n' || c == '\\' || c == '0' || c == '\'' || c == '\"')) {
                    return -1;
                }
                i++;

            } else if (c == '\"') {
                return -1;
            }
        }
        return charCount + 1;
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

    public boolean isCopiedFromIDN() {
        return copiedFromIDN;
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
