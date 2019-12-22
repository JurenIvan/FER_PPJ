package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_PARAMETARA;

public class ListaParametara extends Node {

    public ListaParametara(Node parent) {
        super(parent, LISTA_PARAMETARA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
