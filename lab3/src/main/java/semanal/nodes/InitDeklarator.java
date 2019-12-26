package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public Type type;

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    /*
     * 68. str
     *
     * <init_DEKLARATOR> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ ZAREZ
     * <init_deklarator>
     */

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        if (hasNChildren(2)) {
            ImeTipa imeTipa = getChild(0);
            ListaInitDeklaratora listaInitDeklaratora = getChild(1);

            // TODO
            // this.type = imeTipa.type;

            listaInitDeklaratora.type = this.type;

            addNodeCheckToTasks(imeTipa);
            addNodeCheckToTasks(listaInitDeklaratora);
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
