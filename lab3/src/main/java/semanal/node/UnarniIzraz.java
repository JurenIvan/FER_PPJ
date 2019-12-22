package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_IZRAZ;

public class UnarniIzraz extends Node {

    public UnarniIzraz(Node parent) {
        super(parent, UNARNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
