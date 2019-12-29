package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.ODNOSNI_IZRAZ;
import static semanal.types.NumberType.INT;

public class OdnosniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public OdnosniIzraz(Node parent) {
        super(parent, ODNOSNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 58. str <--o
        o---------------o

        <odnosni_izraz> ::=
			<aditivni_izraz>
			| <odnosni_izraz> OP_LT <aditivni_izraz>
			| <odnosni_izraz> OP_GT <aditivni_izraz>
			| <odnosni_izraz> OP_LTE <aditivni_izraz>
			| <odnosni_izraz> OP_GTE <aditivni_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                AditivniIzraz aditivniIzraz = getChild(0);

                addNodeCheckToTasks(aditivniIzraz);

                addProcedureToTasks(() -> {
                    type = aditivniIzraz.type;
                    leftAssignableExpression = aditivniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                OdnosniIzraz odnosniIzraz = getChild(0);
                AditivniIzraz aditivniIzraz = getChild(2);

                addNodeCheckToTasks(odnosniIzraz);
                addErrorCheckToTasks(() -> odnosniIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(aditivniIzraz);
                addErrorCheckToTasks(() -> aditivniIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
