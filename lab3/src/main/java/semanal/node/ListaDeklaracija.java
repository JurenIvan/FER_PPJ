package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_DEKLARACIJA;

public class ListaDeklaracija extends Node {

    public ListaDeklaracija(Node parent) {
        super(parent, LISTA_DEKLARACIJA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
