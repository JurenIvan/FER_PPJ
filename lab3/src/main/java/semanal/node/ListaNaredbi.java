package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_NAREDBI;

public class ListaNaredbi extends Node {

    public ListaNaredbi(Node parent) {
        super(parent, LISTA_NAREDBI);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
