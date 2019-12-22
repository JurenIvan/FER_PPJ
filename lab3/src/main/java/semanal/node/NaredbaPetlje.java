package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_PETLJE;

public class NaredbaPetlje extends Node {

    public NaredbaPetlje(Node parent) {
        super(parent, NAREDBA_PETLJE);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
