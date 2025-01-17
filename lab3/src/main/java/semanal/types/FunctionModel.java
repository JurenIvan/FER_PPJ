package semanal.types;

import java.util.List;
import java.util.Objects;

public class FunctionModel {

    private List<Type> parameterTypes;
    private List<String> parameterNames;
    private Type returnValueType;
    private boolean defined = false;

    public FunctionModel(List<Type> parameterTypes, List<String> parameterNames, Type returnValueType) {
        Objects.requireNonNull(returnValueType);
        Objects.requireNonNull(parameterTypes);
        Objects.requireNonNull(parameterNames);

        if (!validReturnType(returnValueType)) {
            throw new IllegalArgumentException("Return value type of function must be either non const int/char or void.");
        }

        if (!voidParameters(parameterTypes)) {
            if (parameterTypes.isEmpty() || parameterNames.stream().anyMatch(String::isEmpty)) {
                throw new IllegalArgumentException("Parameters cannot be missing");
            }

            if (parameterTypes.stream().anyMatch(x -> !validParamType(x))) {
                throw new IllegalArgumentException("Illegal parameters given.");
            }

            if (parameterNames.size() != parameterTypes.size() || parameterNames.stream().anyMatch(String::isEmpty)) {
                throw new IllegalArgumentException("Illegal parameter names.");
            }
        }

        this.parameterTypes = parameterTypes;
        this.parameterNames = parameterNames;
        this.returnValueType = returnValueType;
    }

    public FunctionModel(List<Type> parameterTypes, List<String> parameterNames, Type returnValueType, boolean defined) {
        this(parameterTypes, parameterNames, returnValueType);
        this.defined = defined;
    }

    private static boolean voidParameters(List<Type> parameters) {
        return parameters.size() == 1 && parameters.get(0).getSubType() == SubType.VOID;
    }

    public static boolean validReturnType(Type type) {
        if (type == null)
            return false;

        return type.getSubType() == SubType.NUMBER && type.getNumber().isNotConst() || Type.VOID_TYPE.equals(type);
    }

    public static boolean validParamType(Type type) {
        return type.getSubType() == SubType.ARRAY || type.getSubType() == SubType.NUMBER;
    }

    public boolean isDefined() {
        return defined;
    }

    public void setDefined(boolean defined) {
        this.defined = defined;
    }

    public boolean acceptsVoid() {
        return voidParameters(parameterTypes);
    }

    public boolean parameterCheck(List<Type> types, List<String> names) {
        if (types.size() != parameterTypes.size() || names.size() != parameterNames.size())
            return false;

        for (int i = 0; i < parameterTypes.size(); i++) {
            if (!parameterTypes.get(i).equals(types.get(i)))
                return false;
        }

        return true;
    }

    public boolean argumentCheck(List<Type> arguments) {
        if (arguments == null || arguments.size() != parameterTypes.size())
            return false;

        for (int i = 0; i < arguments.size(); i++) {
            Type argType = arguments.get(i);
            Type paramType = parameterTypes.get(i);

            if (argType.getSubType() != paramType.getSubType())
                return false;

            if (argType.getSubType() == SubType.NUMBER && !NumberType.implicitConvertInto(argType, paramType.getNumber()))
                return false;

            if (argType.getSubType() == SubType.ARRAY && !argType.getArray().getNumberType().equals(paramType.getArray().getNumberType()))
                return false;
        }
        return true;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FunctionModel))
            return false;
        FunctionModel that = (FunctionModel) o;
        return getParameterTypes().equals(that.getParameterTypes()) && getReturnValueType().equals(that.getReturnValueType());
    }

    @Override public int hashCode() {
        return Objects.hash(getParameterTypes(), getReturnValueType());
    }

    public Type getReturnValueType() {
        return returnValueType;
    }
}
