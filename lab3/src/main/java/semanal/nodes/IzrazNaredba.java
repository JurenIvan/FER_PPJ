package semanal.nodes;

import semanal.Node;
import semanal.types.NumberType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_NAREDBA;

public class IzrazNaredba extends Node {

    public Type type;

    public IzrazNaredba(Node parent) {
        super(parent, IZRAZ_NAREDBA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 63. str <--o
        o---------------o

		<izraz_naredba> ::=
			TOCKAZAREZ
			| <izraz> TOCKAZAREZ

         */

        switch (getChildrenNumber()) {
            case 1: {
                type = Type.createNumber(NumberType.INT);
                break;
            }
            case 2: {
                Izraz izraz = getChild(0);
                addNodeCheckToTasks(izraz);

                addProcedureToTasks(() -> type = izraz.type);
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
