package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA;

public class Naredba extends Node {

    public Naredba(Node parent) {
        super(parent, NAREDBA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();


         /*
        o---------------o
        o--> 63. str <--o
        o---------------o

		<naredba> ::=
			<slozena_naredba>
			| <izraz_naredba>
			| <naredba_grananja>
			| <naredba_petlje>
			| <naredba_skoka>

         */

        switch (getChildrenNumber()) {
            case 1: {
                addNodeCheckToTasks(getChild(0));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
