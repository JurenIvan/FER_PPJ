package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_PARAMETARA;

public class ListaParametara extends Node {

    public ListaParametara(Node parent) {
        super(parent, LISTA_PARAMETARA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
