package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_PETLJE;

public class NaredbaPetlje extends Node {

    public NaredbaPetlje(Node parent) {
        super(parent, NAREDBA_PETLJE);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
