package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;

public class Izraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public Izraz(Node parent) {
        super(parent, IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 61. str <--o
        o---------------o

        <izraz> ::=
			<izraz_pridruzivanja>
			| <izraz> ZAREZ <izraz_pridruzivanja>

         */

        switch (getChildrenNumber()) {
            case 1: {
                IzrazPridruzivanja izrazPridruzivanja = getChild(0);

                // 1.
                addNodeCheckToTasks(izrazPridruzivanja);

                addProcedureToTasks(() -> {
                    type = izrazPridruzivanja.type;
                    leftAssignableExpression = izrazPridruzivanja.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                Izraz izraz = getChild(0);

                IzrazPridruzivanja izrazPridruzivanja = getChild(2);

                // 1.
                addNodeCheckToTasks(izraz);
                // 2.
                addNodeCheckToTasks(izrazPridruzivanja);

                addProcedureToTasks(() -> {
                    type = izrazPridruzivanja.type;
                    leftAssignableExpression = false;
                });
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
