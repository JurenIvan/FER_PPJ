package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.PRIJEVODNA_JEDINICA;

public class PrijevodnaJedinica extends Node {

    public PrijevodnaJedinica(Node parent) {
        super(parent, PRIJEVODNA_JEDINICA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();


         /*
        o---------------o
        o--> 65. str <--o
        o---------------o

		<prijevodna_jedinica> ::=
			<vanjska_deklaracija>
			| <prijevodna_jedinica> <vanjska_deklaracija>

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
