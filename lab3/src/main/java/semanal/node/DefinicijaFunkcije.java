package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.DEFINICIJA_FUNKCIJE;

public class DefinicijaFunkcije extends Node {

    public DefinicijaFunkcije(Node parent) {
        super(parent, DEFINICIJA_FUNKCIJE);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
