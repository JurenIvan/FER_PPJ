package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_INIT_DEKLARATORA;

public class ListaInitDeklaratora extends Node {

    public ListaInitDeklaratora(Node parent) {
        super(parent, LISTA_INIT_DEKLARATORA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
