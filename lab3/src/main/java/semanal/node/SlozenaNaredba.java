package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.SLOZENA_NAREDBA;

public class SlozenaNaredba extends Node {

    public SlozenaNaredba(Node parent) {
        super(parent, SLOZENA_NAREDBA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
