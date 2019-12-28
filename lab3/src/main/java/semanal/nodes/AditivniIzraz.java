package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.ADITIVNI_IZRAZ;
import static semanal.types.NumberType.INT;

public class AditivniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public AditivniIzraz(Node parent) {
        super(parent, ADITIVNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 57. str <--o
        o---------------o

        <aditivni_izraz> ::=
			<multiplikativni_izraz>
			| <aditivni_izraz> PLUS <multiplikativni_izraz>
			| <aditivni_izraz> MINUS <multiplikativni_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                MultiplikativniIzraz multiplikativniIzraz = getChild(0);

                addNodeCheckToTasks(multiplikativniIzraz);

                addProcedureToTasks(() -> {
                    type = multiplikativniIzraz.type;
                    leftAssignableExpression = multiplikativniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                AditivniIzraz aditivniIzraz = getChild(2);
                MultiplikativniIzraz multiplikativniIzraz = getChild(0);

                addNodeCheckToTasks(aditivniIzraz);
                addErrorCheckToTasks(() -> aditivniIzraz.type.getNumber().implicitConvertInto(INT));
                addNodeCheckToTasks(multiplikativniIzraz);
                addErrorCheckToTasks(() -> multiplikativniIzraz.type.getNumber().implicitConvertInto(INT));

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



