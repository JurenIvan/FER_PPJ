package semanal;

import semanal.variables.Variable;
import semanal.variables.VariableResult;
import semanal.variables.VariableType;

import java.util.LinkedList;
import java.util.Objects;

public class MemoryScope<OF extends Variable> {

    private LinkedList<OF> memory = new LinkedList<>();
    private MemoryScope<OF> previous;

    public MemoryScope(MemoryScope<OF> previous) {
        this.previous = previous;
    }

    private int getIndexOfVariableInCurrentScope(String variableName) {
        Objects.requireNonNull(variableName);
        int index = 0;
        for (Variable var : memory) {
            if (var.getName().equals(variableName)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private boolean doesCurrentScopeContainName(String variableName) {
        Objects.requireNonNull(variableName);
        return getIndexOfVariableInCurrentScope(variableName) != -1;
    }

    public boolean check(String variableName) {
        Objects.requireNonNull(variableName);
        if (isGlobal()) {
            return doesCurrentScopeContainName(variableName);
        } else {
            if (doesCurrentScopeContainName(variableName)) {
                return true;
            }
            if (previous != null) {
                return previous.check(variableName);
            }
            return false;
        }
    }

    public boolean checkIfGlobal(String variableName) {
        Objects.requireNonNull(variableName);
        if (isGlobal()) {
            return doesCurrentScopeContainName(variableName);
        } else {
            if (previous != null) {
                return previous.checkIfGlobal(variableName);
            }
            return false;
        }
    }

    public boolean checkIfLocal(String variableName) {
        Objects.requireNonNull(variableName);
        if (doesCurrentScopeContainName(variableName)) {
            return true;
        } else {
            if (previous != null && !previous.isGlobal())
                return previous.checkInScope(variableName);
            return false;
        }
    }

    public boolean checkInScope(String variableName) {
        return doesCurrentScopeContainName(variableName);
    }

    public VariableResult get(String variableName) {
        Objects.requireNonNull(variableName);
        return get(variableName, 0);
    }

    private VariableResult get(String variableName, int heapPosition) {
        Objects.requireNonNull(variableName);
        int index = getIndexOfVariableInCurrentScope(variableName);
        if (index != -1) {

            Variable var = null;
            int deltaPosition = 0;
            for (Variable variable : memory) {
                if (index > 0) {
                    deltaPosition += variable.getSize();
                    index--;
                } else {
                    var = variable;
                    break;
                }
            }

            if (var.getVariableType() == VariableType.HEAP_ELEMENT) {
                return VariableResult.FromHeapElement(var, deltaPosition + heapPosition);
            } else if (var.getVariableType() == VariableType.LABEL_ELEMENT) {
                return VariableResult.FromLabel(var);
            } else {
                return VariableResult.FromFunction(var);
            }
        }
        if (previous != null) {
            return previous.get(variableName, heapPosition + getSizeInBytes());
        }
        throw new IllegalArgumentException("Variable not in memory.");
    }

    public LinkedList<OF> getMemory() {
        return memory;
    }

    public void define(OF variable) {
        Objects.requireNonNull(variable);
        if (!doesCurrentScopeContainName(variable.getName())) {
            if (variable.getVariableType() == VariableType.LABEL_ELEMENT && !isGlobal()) {
                throw new IllegalArgumentException("Can only add global variables to global scope, not " + variable.getVariableType());
            }
            memory.addFirst(variable);
        }
    }

    public OF set(OF variable) {
        Objects.requireNonNull(variable);
        int index = getIndexOfVariableInCurrentScope(variable.getName());
        if (index != -1) {
            if (variable.getVariableType() == VariableType.LABEL_ELEMENT && !isGlobal()) {
                throw new IllegalArgumentException("Can only add global variables to global scope, not " + variable.getVariableType());
            }
            return memory.set(index, variable);
        }
        if (previous != null) {
            return previous.set(variable);
        }
        throw new IllegalArgumentException("Variable not in memory.");
    }

    public boolean isGlobal() {
        return previous == null;
    }

    public int getSizeInBytes() {
        int result = 0;
        for (Variable var : memory) {
            result += var.getSize();
        }
        return result;
    }

    public int getSizeOfLocalScopeInBytes() {
        return getSizeInBytes() + (previous != null && !previous.isGlobal() ? previous.getSizeOfLocalScopeInBytes() : 0);
    }
}
