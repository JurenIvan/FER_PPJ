package semanal.variables;

import semanal.types.Type;

import java.util.Objects;

public class Variable {
    private String name;
    private VariableType variableType;
    private int size; // in memory locations, size=1 means 32b aka 4B
    private Type elementType;
    private String labelName;

    private Variable(String name, Type elementType, int size, VariableType variableType, String labelName) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(elementType);

        this.name = name;
        this.elementType = elementType;
        this.size = size;
        this.variableType = variableType;
        this.labelName = labelName;
    }

    public static Variable AsLabel(String name, Type elementType, int size, String labelName) {
        Objects.requireNonNull(labelName);
        return new Variable(name, elementType, size, VariableType.LABEL_ELEMENT, labelName);
    }

    public static Variable AsHeapElement(String name, Type elementType, int size) {
        return new Variable(name, elementType, size, VariableType.HEAP_ELEMENT, null);
    }

    public static Variable AsFunction(String name, Type elementType) {
        return new Variable(name, elementType, 0, VariableType.FUNCTION, null);
    }

    public Type getElementType() {
        return elementType;
    }

    public int getSize() {
        return size;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public String getLabelName() {
        if (variableType != VariableType.LABEL_ELEMENT)
            throw new IllegalArgumentException("Cannot access label of a heap element.");

        return labelName;
    }

    public String getName() {
        return name;
    }
}
