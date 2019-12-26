package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public Type type;

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
