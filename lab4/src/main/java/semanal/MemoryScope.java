package semanal;

import java.util.HashMap;

public class MemoryScope<OF> {

    private HashMap<String, OF> memory = new HashMap<>();
    private MemoryScope<OF> previous;

    public MemoryScope(MemoryScope<OF> previous) {
        this.previous = previous;
    }

    public boolean checkGlobal(String variable) {
        if (memory.containsKey(variable)) return true;
        if (previous != null) return previous.checkGlobal(variable);
        return false;
    }

    public boolean checkLocal(String variable) {
        return memory.containsKey(variable);
    }

    public OF get(String variable) {
        if (memory.containsKey(variable)) return memory.get(variable);
        if (previous != null) return previous.get(variable);
        throw new IllegalArgumentException("Variable not in memory.");
    }

    public HashMap<String, OF> getMemory() {
        return memory;
    }

    public void define(String variable, OF value) {
        if (!memory.containsKey(variable)) memory.put(variable, value);
    }

    public OF set(String variable, OF value) {
        if (memory.containsKey(variable)) return memory.put(variable, value);
        if (previous != null) return previous.set(variable, value);
        throw new IllegalArgumentException("Variable not in memory.");
    }

    public MemoryScope<OF> getPrevious() {
        return previous;
    }
}
