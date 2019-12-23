package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_SKOKA;

public class NaredbaSkoka extends Node {

    public NaredbaSkoka(Node parent) {
        super(parent, NAREDBA_SKOKA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
