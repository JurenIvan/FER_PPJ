package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_IZRAZA_PRIDRUZIVANJA;

public class ListaIzrazaPridruzivanja extends Node {

    public ListaIzrazaPridruzivanja(Node parent) {
        super(parent, LISTA_IZRAZA_PRIDRUZIVANJA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
