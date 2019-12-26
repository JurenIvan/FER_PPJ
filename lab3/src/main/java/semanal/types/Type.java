package semanal.types;

import java.util.List;
import java.util.Objects;

public class Type {

    private SubType subType;
    private NumberType number;
    private FunctionModel function;
    private ArrayModel array;

    private Type(SubType subType) {
        this.subType = Objects.requireNonNull(subType);
    }

    public static Type createVoid() {
        return new Type(SubType.VOID);
    }

    public static Type createNumber(NumberType numberType) {
        Type number = new Type(SubType.NUMBER);
        number.number = numberType;
        return number;
    }

    public static Type createMethod(List<Type> parameterTypes, Type returnValueType) {
        Type method = new Type(SubType.NUMBER);
        method.function = new FunctionModel(parameterTypes, returnValueType);
        return method;
    }

    public static Type createArray(NumberType numberType) {
        Type array = new Type(SubType.ARRAY);
        array.array = new ArrayModel(numberType);
        return array;
    }

    public SubType getSubType() {
        return subType;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Type))
            return false;
        Type type = (Type) o;
        return getSubType() == type.getSubType() && getNumber() == type.getNumber() && Objects.equals(getFunction(), type.getFunction())
                && Objects.equals(getArray(), type.getArray());
    }

    @Override public int hashCode() {
        return Objects.hash(getSubType(), getNumber(), getFunction(), getArray());
    }

    public NumberType getNumber() {
        return number;
    }

    public FunctionModel getFunction() {
        return function;
    }

    public ArrayModel getArray() {
        return array;
    }
}