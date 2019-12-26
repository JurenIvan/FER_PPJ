package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_DEKLARACIJA;

public class ListaDeklaracija extends Node {

    public ListaDeklaracija(Node parent) {
        super(parent, LISTA_DEKLARACIJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
