package semanal;

import java.util.HashMap;

public class MemoryScope<OF> {

    private HashMap<String, OF> memory;
    private MemoryScope<OF> previous;

    public MemoryScope(MemoryScope<OF> previous) {
        this.previous = previous;
    }

    public boolean check(String variable) {
        if (memory.containsKey(variable)) return true;
        if (previous != null) return previous.check(variable);
        return false;
    }

    public OF get(String variable) {
        if (memory.containsKey(variable)) return memory.get(variable);
        if (previous != null) return previous.get(variable);
        return null; //todo dal da bacim exception?
    }

    public void define(String variable, OF value) {
        if (!memory.containsKey(variable)) memory.put(variable, value);
    }

    public OF set(String variable, OF value) {
        if (memory.containsKey(variable)) return memory.put(variable, value);
        if (previous != null) return previous.set(variable, value);
        return null; //todo dal da bacim exception
    }


    public MemoryScope<OF> getPrevious() {
        return previous;
    }
}
