package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.SPECIFIKATOR_TIPA;

public class SpecifikatorTipa extends Node {

    public SpecifikatorTipa(Node parent) {
        super(parent, SPECIFIKATOR_TIPA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
