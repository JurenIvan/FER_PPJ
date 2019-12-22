package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.MULTIPLIKATIVNI_IZRAZ;

public class MultiplikativniIzraz extends Node {

    public MultiplikativniIzraz(Node parent) {
        super(parent, MULTIPLIKATIVNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
