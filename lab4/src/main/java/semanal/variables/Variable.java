package semanal.variables;

import semanal.types.Type;

import java.util.Objects;

import static semanal.variables.VariableType.*;

public class Variable {
    private final String name;
    private final VariableType variableType;
    private final int size; // in bytes, size=4 means 32b aka 1 memory locations
    private final Type elementType;
    private final String labelName;

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
        return new Variable(name, elementType, size, LABEL_ELEMENT, labelName);
    }

    public static Variable AsHeapElement(String name, Type elementType, int size) {
        return new Variable(name, elementType, size, HEAP_ELEMENT, null);
    }

    public static Variable AsFunction(String name, Type elementType) {
        return new Variable(name, elementType, 0, FUNCTION, null);
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
        if (variableType != LABEL_ELEMENT)
            throw new IllegalArgumentException("Cannot access label of a heap element.");

        return labelName;
    }

    public String getName() {
        return name;
    }
}
