package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.ODNOSNI_IZRAZ;

public class OdnosniIzraz extends Node {

    public OdnosniIzraz(Node parent) {
        super(parent, ODNOSNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
