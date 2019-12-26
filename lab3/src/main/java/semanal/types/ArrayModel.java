package semanal.types;

import semanal.Utils;

import java.util.Objects;

public class ArrayModel {
    private Type elementType;

    public ArrayModel(NumberType numberType) {
        this.elementType = Type.createNumber(numberType);
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

                if (!(c == 't' || c == 'n' || c == '\\' || c == '0' || c == '\'' || c == '\"')) {
                    return false;
                }

                i++;
            }
        }

        return true;
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
        // TODO not sure if Ive understood it correctly that only the following is permitted:
        //      niz(T) --> niz(const(T))

        if (getNumberType().getPrimitiveNumberType() != other.getNumberType().getPrimitiveNumberType())
            return false;

        if (getNumberType().isConst())
            return false;

        return true;
    }

}