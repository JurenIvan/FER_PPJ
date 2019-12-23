package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA_PARAMETRA;

public class DeklaracijaParametra extends Node {

    public DeklaracijaParametra(Node parent) {
        super(parent, DEKLARACIJA_PARAMETRA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
