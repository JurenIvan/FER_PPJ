package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.ADITIVNI_IZRAZ;

public class AditivniIzraz extends Node {

    public AditivniIzraz(Node parent) {
        super(parent, ADITIVNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}



