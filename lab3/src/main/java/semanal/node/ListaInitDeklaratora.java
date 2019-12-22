package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_INIT_DEKLARATORA;

public class ListaInitDeklaratora extends Node {

    public ListaInitDeklaratora(Node parent) {
        super(parent, LISTA_INIT_DEKLARATORA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
