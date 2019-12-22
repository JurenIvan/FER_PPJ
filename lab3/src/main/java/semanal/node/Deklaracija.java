package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA;

public class Deklaracija extends Node {

    public Deklaracija(Node parent) {
        super(parent, DEKLARACIJA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
