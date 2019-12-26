package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAVNI_DEKLARATOR;

public class IzravniDeklarator extends Node {

    public IzravniDeklarator(Node parent) {
        super(parent, IZRAVNI_DEKLARATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
