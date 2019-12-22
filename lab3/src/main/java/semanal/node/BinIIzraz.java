package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_I_IZRAZ;

public class BinIIzraz extends Node {

    public BinIIzraz(Node parent) {
        super(parent, BIN_I_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
