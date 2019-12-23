package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
