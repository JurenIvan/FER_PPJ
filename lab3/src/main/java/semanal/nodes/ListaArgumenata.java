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

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 54. str <--o
        o---------------o

        <lista_argumenata> ::=
            <izraz_pridruzivanja>
        	| <lista_argumenata> ZAREZ <izraz_pridruzivanja>

         */

        if (hasNChildren(1)) {
            IzrazPridruzivanja izrazPridruzivanja = getChild(0);
            addNodeCheckToTasks(izrazPridruzivanja);
        } else if (hasNChildren(2)) {
            ListaArgumenata listaArgumenata = getChild(0);
            IzrazPridruzivanja izrazPridruzivanja = getChild(1);

            addNodeCheckToTasks(listaArgumenata);
            addNodeCheckToTasks(izrazPridruzivanja);
            addProcedureToTasks(() -> {
                argumentTypes.addAll(listaArgumenata.argumentTypes);
                argumentTypes.add(izrazPridruzivanja.type);
            });

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
