package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LOG_ILI_IZRAZ;

public class LogIliIzraz extends Node {

    public LogIliIzraz(Node parent) {
        super(parent, LOG_ILI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
