package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.VANJSKA_DEKLARACIJA;

public class VanjskaDeklaracija extends Node {

    public VanjskaDeklaracija(Node parent) {
        super(parent, VANJSKA_DEKLARACIJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
