package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.SLOZENA_NAREDBA;

public class SlozenaNaredba extends Node {

    public boolean createLocalScope = true;

    public SlozenaNaredba(Node parent) {
        super(parent, SLOZENA_NAREDBA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

         /*
        o---------------o
        o--> 62. str <--o
        o---------------o

        <slozena_naredba> ::=
			L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
			| L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA

         */

        addProcedureToTasks(() -> {
            if (createLocalScope) {
                createLocalVariableMemory();
            }
        });

        switch (getChildrenNumber()) {
            case 3: {
                addNodeCheckToTasks(getChild(1));
                break;
            }
            case 4: {
                addNodeCheckToTasks(getChild(1));
                addNodeCheckToTasks(getChild(2));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
