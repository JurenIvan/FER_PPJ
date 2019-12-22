package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAVNI_DEKLARATOR;

public class IzravniDeklarator extends Node {

    public IzravniDeklarator(Node parent) {
        super(parent, IZRAVNI_DEKLARATOR);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
