package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_IZRAZA_PRIDRUZIVANJA;

public class ListaIzrazaPridruzivanja extends Node {

    public ListaIzrazaPridruzivanja(Node parent) {
        super(parent, LISTA_IZRAZA_PRIDRUZIVANJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
