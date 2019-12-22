package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.IME_TIPA;

public class ImeTipa extends Node {

    public ImeTipa(Node parent) {
        super(parent, IME_TIPA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
