package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_GRANANJA;

public class NaredbaGrananja extends Node {

    public NaredbaGrananja(Node parent) {
        super(parent, NAREDBA_GRANANJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
