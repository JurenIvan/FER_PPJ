package semanal.types;

import java.util.List;
import java.util.Objects;

public class FunctionModel {

    private List<Type> parameterTypes;
    private Type returnValueType;
    private boolean defined = false;

    public FunctionModel(List<Type> parameterTypes, Type returnValueType) {
        Objects.requireNonNull(returnValueType);
        Objects.requireNonNull(parameterTypes);

        if (parameterTypes.isEmpty()) {
            throw new IllegalArgumentException("Parameters cannot be missing");
        }

        if (!validNumberParameter(returnValueType) && returnValueType.getSubType() != SubType.VOID) {
            throw new IllegalArgumentException("Return value type of function must be either int/char or void.");
        }

        if (!voidParameters(parameterTypes)) {
            if (parameterTypes.stream().anyMatch(x -> !validNumberParameter(x))) {
                throw new IllegalArgumentException(
                        "Illegal parameters given. Function parameters can either be of type char/int or a single parameter of type void.");
            }
        }

        this.parameterTypes = parameterTypes;
        this.returnValueType = returnValueType;
    }

    public FunctionModel(List<Type> parameterTypes, Type returnValueType, boolean defined) {
        this(parameterTypes, returnValueType);
        this.defined = defined;
    }

    private static boolean voidParameters(List<Type> parameters) {
        return parameters.size() == 1 && parameters.get(0).getSubType() == SubType.VOID;
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

    private boolean validNumberParameter(Type type) {
        return type.getSubType() == SubType.NUMBER && !type.getNumber().isConst();
    }

    public boolean argumentCheck(List<Type> arguments) {
        for (int i = 0; i < arguments.size(); i++) {
            Type argType = arguments.get(i);
            Type paramType = parameterTypes.get(i);

            if (argType.getSubType() != paramType.getSubType())
                return false;

            if (argType.getSubType() == SubType.NUMBER && NumberType.implicitConvertInto(argType, paramType.getNumber()))
                return false;
        }
        return true;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
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
