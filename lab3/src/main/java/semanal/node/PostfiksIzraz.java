package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.POSTFIKS_IZRAZ;

public class PostfiksIzraz extends Node {

    public PostfiksIzraz(Node parent) {
        super(parent, POSTFIKS_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
