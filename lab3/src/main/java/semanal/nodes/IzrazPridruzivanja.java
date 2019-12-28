package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_PRIDRUZIVANJA;

public class IzrazPridruzivanja extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public IzrazPridruzivanja(Node parent) {
        super(parent, IZRAZ_PRIDRUZIVANJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 61. str <--o
        o---------------o

        <izraz_pridruzivanja> ::=
			<log_ili_izraz>
			| <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>

         */

        switch (getChildrenNumber()) {
            case 1: {
                LogIliIzraz logIliIzraz = getChild(0);

                addNodeCheckToTasks(logIliIzraz);
                addProcedureToTasks(() -> {
                    type = logIliIzraz.type;
                    leftAssignableExpression = logIliIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                PostfiksIzraz postfiksIzraz = getChild(0);
                IzrazPridruzivanja izrazPridruzivanja = getChild(2);

                addNodeCheckToTasks(postfiksIzraz);
                addErrorCheckToTasks(() -> postfiksIzraz.leftAssignableExpression);
                addNodeCheckToTasks(izrazPridruzivanja);
                addErrorCheckToTasks(() -> izrazPridruzivanja.type.implicitConvertInto(postfiksIzraz.type));

                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type;
                    leftAssignableExpression = false;
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
