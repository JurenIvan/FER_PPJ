package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.List;

import static semanal.NodeType.LISTA_IZRAZA_PRIDRUZIVANJA;

public class ListaIzrazaPridruzivanja extends Node {

    public List<Type> argumentTypes = new ArrayList<>();
    public int numberOfElements = 0;

    public ListaIzrazaPridruzivanja(Node parent) {
        super(parent, LISTA_IZRAZA_PRIDRUZIVANJA);
    }
    /*
    o---------------o
    o--> 61. str <--o
    o---------------o

    <lista_izraza_pridruzivanja> ::=
        <izraz_pridruzivanja>
        | <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>

     */

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
        if (hasNChildren(1)) {
            IzrazPridruzivanja izrazPridruzivanja = getChild(0);

            addNodeCheckToTasks(izrazPridruzivanja);

            addProcedureToTasks(() -> {
                numberOfElements = 1;
                argumentTypes.add(izrazPridruzivanja.type);
            });
        } else if (hasNChildren(3)) {
            ListaIzrazaPridruzivanja listaIzrazaPridruzivanja = getChild(0);
            IzrazPridruzivanja izrazPridruzivanja = getChild(2);

            addNodeCheckToTasks(listaIzrazaPridruzivanja);
            addNodeCheckToTasks(izrazPridruzivanja);

            addProcedureToTasks(() -> {
                numberOfElements = listaIzrazaPridruzivanja.numberOfElements++;
                argumentTypes.addAll(listaIzrazaPridruzivanja.argumentTypes);
                argumentTypes.add(izrazPridruzivanja.type);
            });

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
