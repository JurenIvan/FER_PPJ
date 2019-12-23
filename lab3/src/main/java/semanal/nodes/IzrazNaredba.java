package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_NAREDBA;

public class IzrazNaredba extends Node {

    public IzrazNaredba(Node parent) {
        super(parent, IZRAZ_NAREDBA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
