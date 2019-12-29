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

        /*
         * o---------------o
         * o--> 68. str <--o
         * o---------------o
         *
         * <lista_deklaracija> ::=
         *      <deklaracija>
         *      | <lista_deklaracija> <deklaracija>
         *
         */

        if (hasNChildren(1)) {
            Deklaracija deklaracija = getChild(0);

            addNodeCheckToTasks(deklaracija);
            
        } else if (hasNChildren(2)) {
            ListaDeklaracija listaDeklaracija = getChild(0);
            Deklaracija deklaracija = getChild(1);

            addNodeCheckToTasks(listaDeklaracija);
            addNodeCheckToTasks(deklaracija);

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
