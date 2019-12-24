package semanal.types;

import java.util.List;
import java.util.Objects;

public class FunctionModel {

    private List<Type> parameterTypes;
    private Type returnValueType;

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
            if (parameterTypes.stream().filter(x -> !validNumberParameter(x)).count() > 0) {
                throw new IllegalArgumentException(
                        "Illegal parameters given. Function parameters can either be of type char/int or a single parameter of type void.");
            }
        }

        this.parameterTypes = parameterTypes;
        this.returnValueType = returnValueType;
    }

    private static boolean voidParameters(List<Type> parameters) {
        return parameters.size() == 1 && parameters.get(0).getSubType() == SubType.VOID;
    }

    public boolean acceptsVoid() {
        return voidParameters(parameterTypes);
    }

    private boolean validNumberParameter(Type type) {
        return type.getSubType() == SubType.NUMBER && !type.getNumber().isConst();
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
