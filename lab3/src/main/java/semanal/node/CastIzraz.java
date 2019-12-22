package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.CAST_IZRAZ;

public class CastIzraz extends Node {

    public CastIzraz(Node parent) {
        super(parent, CAST_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
