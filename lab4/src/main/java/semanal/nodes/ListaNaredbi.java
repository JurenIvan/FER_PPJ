package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LISTA_NAREDBI;

public class ListaNaredbi extends Node {

    public ListaNaredbi(Node parent) {
        super(parent, LISTA_NAREDBI);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

         /*
        o---------------o
        o--> 62. str <--o
        o---------------o

		<lista_naredbi> ::=
			<naredba>
			| <lista_naredbi> <naredba>

         */

        switch (getChildrenNumber()) {
            case 1: {
                addNodeCheckToTasks(getChild(0));
                break;
            }
            case 2: {
                addNodeCheckToTasks(getChild(0));
                addNodeCheckToTasks(getChild(1));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
