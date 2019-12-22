package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
