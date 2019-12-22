package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_ILI_IZRAZ;

public class BinIliIzraz extends Node {

    public BinIliIzraz(Node parent) {
        super(parent, BIN_ILI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
