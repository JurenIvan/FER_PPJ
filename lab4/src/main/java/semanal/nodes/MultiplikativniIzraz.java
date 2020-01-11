package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.MULTIPLIKATIVNI_IZRAZ;
import static semanal.types.NumberType.INT;

public class MultiplikativniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public MultiplikativniIzraz(Node parent) {
        super(parent, MULTIPLIKATIVNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 57. str <--o
        o---------------o

        <multiplikativni_izraz> ::=
            <cast_izraz>
            | <multiplikativni_izraz> OP_PUTA <cast_izraz>
            | <multiplikativni_izraz> OP_DIJELI <cast_izraz>
            | <multiplikativni_izraz> OP_MOD <cast_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                CastIzraz castIzraz = getChild(0);

                addNodeCheckToTasks(castIzraz);

                addProcedureToTasks(() -> {
                    type = castIzraz.type;
                    leftAssignableExpression = castIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                MultiplikativniIzraz multiplikativniIzraz = getChild(0);
                CastIzraz castIzraz = getChild(2);

                addNodeCheckToTasks(multiplikativniIzraz);
                addErrorCheckToTasks(() -> multiplikativniIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(castIzraz);
                addErrorCheckToTasks(() -> castIzraz.type.implicitConvertInto(INT));

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
