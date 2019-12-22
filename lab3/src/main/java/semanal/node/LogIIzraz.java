package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LOG_I_IZRAZ;

public class LogIIzraz extends Node {

    public LogIIzraz(Node parent) {
        super(parent, LOG_I_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
