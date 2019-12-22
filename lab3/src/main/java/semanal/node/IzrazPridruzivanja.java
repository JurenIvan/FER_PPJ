package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_PRIDRUZIVANJA;

public class IzrazPridruzivanja extends Node {

    public IzrazPridruzivanja(Node parent) {
        super(parent, IZRAZ_PRIDRUZIVANJA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
