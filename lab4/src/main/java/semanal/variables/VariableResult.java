package semanal.variables;

import semanal.types.Type;

import java.util.Objects;

public class VariableResult {

    private VariableType variableType;
    private Type elementType;
    private String labelName;
    private Integer position;
    private int size;

    public VariableResult(VariableType variableType, Type elementType, int size, String labelName, Integer position) {
        this.variableType = variableType;
        this.size = size;
        this.labelName = labelName;
        this.position = position;
        this.elementType = elementType;
    }

    public static VariableResult FromLabel(Variable var) {
        Objects.requireNonNull(var);
        if (var.getVariableType() != VariableType.LABEL_ELEMENT) {
            throw new IllegalArgumentException("Given variable is not a label element variable");
        }

        return new VariableResult(VariableType.LABEL_ELEMENT, var.getElementType(), var.getSize(), var.getLabelName(), null);
    }

    public static VariableResult FromHeapElement(Variable var, int position) {
        Objects.requireNonNull(var);
        if (var.getVariableType() != VariableType.HEAP_ELEMENT) {
            throw new IllegalArgumentException("Given variable is not a heap element variable");
        }

        return new VariableResult(VariableType.HEAP_ELEMENT, var.getElementType(), var.getSize(), null, position);
    }

    public static VariableResult FromFunction(Variable var) {
        Objects.requireNonNull(var);
        if (var.getVariableType() != VariableType.FUNCTION) {
            throw new IllegalArgumentException("Given variable is not a function variable");
        }

        return new VariableResult(VariableType.FUNCTION, var.getElementType(), var.getSize(), null, null);
    }

    public Type getElementType() {
        return elementType;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public String getLabelName() {
        return labelName;
    }

    public Integer getPositionInBytes() {
        return position;
    }

    public int getSize() {
        return size;
    }
}
