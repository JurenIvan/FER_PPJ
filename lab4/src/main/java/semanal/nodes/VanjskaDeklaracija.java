package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.VANJSKA_DEKLARACIJA;

public class VanjskaDeklaracija extends Node {

    public VanjskaDeklaracija(Node parent) {
        super(parent, VANJSKA_DEKLARACIJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

         /*
        o---------------o
        o--> 65. str <--o
        o---------------o

		<vanjska_deklaracija> ::=
			<definicija_funkcije>
			| <deklaracija>

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
