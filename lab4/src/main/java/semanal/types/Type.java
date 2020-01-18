package semanal.types;

import java.util.List;
import java.util.Objects;

public class Type {

    public static final Type VOID_TYPE = new Type(SubType.VOID);

    private SubType subType;
    private NumberType number;
    private FunctionModel function;
    private ArrayModel array;

    private Type(SubType subType) {
        this.subType = Objects.requireNonNull(subType);
    }

    public static Type createNumber(NumberType numberType) {
        Type number = new Type(SubType.NUMBER);
        number.number = numberType;
        return number;
    }

    public static Type createFunctionDeclaration(List<Type> parameterTypes, List<String> parameterNames, Type returnValueType) {
        Type function = new Type(SubType.FUNCTION);
        function.function = new FunctionModel(parameterTypes, parameterNames, returnValueType);
        return function;
    }

    public static Type createFunctionDefinition(List<Type> parameterTypes, List<String> parameterNames, Type returnValueType) {
        Type function = new Type(SubType.FUNCTION);
        function.function = new FunctionModel(parameterTypes, parameterNames, returnValueType, true);
        return function;
    }

    public static Type createArray(NumberType numberType, int numberOfElements) {
        Type array = new Type(SubType.ARRAY);
        array.array = new ArrayModel(numberType, numberOfElements, false, false);
        return array;
    }

    public static Type createArrayFromString(NumberType numberType, int numberOfElements) {
        Type array = new Type(SubType.ARRAY);
        array.array = new ArrayModel(numberType, numberOfElements, true, false);
        return array;
    }

    public static Type createArrayFromIDN(Type arrayType) {
        Type array = new Type(SubType.ARRAY);
        array.array = new ArrayModel(arrayType.getArray().getNumberType(), arrayType.getArray().getNumberOfElements(), false, true);
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

    public int getSize() {
        if (subType == SubType.NUMBER) {
            return 1;
        } else if (subType == SubType.ARRAY) {
            return array.getNumberOfElements();
        } else {
            return 0;
        }
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

    public boolean implicitConvertInto(NumberType other) {
        if (subType != SubType.NUMBER)
            return false;

        return getNumber().implicitConvertInto(other);
    }

    public boolean implicitConvertInto(Type other) {
        if (subType != other.subType) {
            return false;
        }

        if (subType == SubType.NUMBER) {
            return getNumber().implicitConvertInto(other.getNumber());
        }
        return false;
    }

    public boolean explicitConvertInto(Type other) {
        if (other == null)
            return false;

        return subType == SubType.NUMBER && other.subType == SubType.NUMBER;
    }

    public boolean isLeftAssignable() {
        if (subType == SubType.NUMBER) {
            return getNumber().isNotConst();
        }
        return false;
    }
}