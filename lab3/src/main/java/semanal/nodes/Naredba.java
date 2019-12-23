package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA;

public class Naredba extends Node {

    public Naredba(Node parent) {
        super(parent, NAREDBA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
