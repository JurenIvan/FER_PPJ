package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_NAREDBA;

public class IzrazNaredba extends Node {

    public IzrazNaredba(Node parent) {
        super(parent, IZRAZ_NAREDBA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
