package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.INICIJALIZATOR;

public class Inicijalizator extends Node {

    public Inicijalizator(Node parent) {
        super(parent, INICIJALIZATOR);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
