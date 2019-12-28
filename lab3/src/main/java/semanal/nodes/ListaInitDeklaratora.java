package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_INIT_DEKLARATORA;

public class ListaInitDeklaratora extends Node {

    public Type nType;

    public ListaInitDeklaratora(Node parent) {
        super(parent, LISTA_INIT_DEKLARATORA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
        /*
        o---------------o
        o--> 68. str <--o
        o---------------o


		<lista_init_deklaratora> ::=
			<init_deklarator>
			| <lista_init_deklaratora> ZAREZ <init_deklarator>

         */

        if (hasNChildren(1)) {
            InitDeklarator initDeklarator = getChild(0);

            initDeklarator.nType = nType;
            addNodeCheckToTasks(initDeklarator);
        } else if (hasNChildren(3)) {
            ListaInitDeklaratora listaInitDeklaratora = getChild(0);
            InitDeklarator initDeklarator = getChild(1);

            listaInitDeklaratora.nType = this.nType;
            addNodeCheckToTasks(listaInitDeklaratora);

            initDeklarator.nType = this.nType;
            addNodeCheckToTasks(initDeklarator);

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
