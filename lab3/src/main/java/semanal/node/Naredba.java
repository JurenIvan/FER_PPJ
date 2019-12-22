package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA;

public class Naredba extends Node {

    public Naredba(Node parent) {
        super(parent, NAREDBA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
