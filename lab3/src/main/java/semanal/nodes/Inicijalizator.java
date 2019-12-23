package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.INICIJALIZATOR;

public class Inicijalizator extends Node {

    public Inicijalizator(Node parent) {
        super(parent, INICIJALIZATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
