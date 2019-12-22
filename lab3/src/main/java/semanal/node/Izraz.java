package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;

public class Izraz extends Node {

    public Izraz(Node parent) {
        super(parent, IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
