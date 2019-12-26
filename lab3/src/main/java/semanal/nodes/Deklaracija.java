package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA;

public class Deklaracija extends Node {

    public Deklaracija(Node parent) {
        super(parent, DEKLARACIJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
