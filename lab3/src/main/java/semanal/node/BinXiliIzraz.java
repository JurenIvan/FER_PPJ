package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_XILI_IZRAZ;

public class BinXiliIzraz extends Node {

    public BinXiliIzraz(Node parent) {
        super(parent, BIN_XILI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
