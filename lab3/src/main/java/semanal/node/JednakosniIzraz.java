package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.JEDNAKOSNI_IZRAZ;

public class JednakosniIzraz extends Node {

    public JednakosniIzraz(Node parent) {
        super(parent, JEDNAKOSNI_IZRAZ);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
