package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.PRIMARNI_IZRAZ;

public class PrimarniIzraz extends Node {

    public PrimarniIzraz(Node parent) {
        super(parent, PRIMARNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
