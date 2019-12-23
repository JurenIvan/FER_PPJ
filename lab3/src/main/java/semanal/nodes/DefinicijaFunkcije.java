package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.DEFINICIJA_FUNKCIJE;

public class DefinicijaFunkcije extends Node {

    public DefinicijaFunkcije(Node parent) {
        super(parent, DEFINICIJA_FUNKCIJE);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
