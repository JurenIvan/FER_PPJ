package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_ARGUMENATA;

public class ListaArgumenata extends Node {

    public ListaArgumenata(Node parent) {
        super(parent, LISTA_ARGUMENATA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
