package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.List;

import static semanal.NodeType.LISTA_ARGUMENATA;

public class ListaArgumenata extends Node {

    public List<Type> argumentTypes;

    public ListaArgumenata(Node parent) {
        super(parent, LISTA_ARGUMENATA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 54. str <--o
        o---------------o

        <lista_argumenata> ::=
            <izraz_pridruzivanja>
        	| <lista_argumenata> ZAREZ <izraz_pridruzivanja>

         */

        // TODO

    }
}
