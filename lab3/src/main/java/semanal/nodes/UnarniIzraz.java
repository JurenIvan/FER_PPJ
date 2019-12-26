package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_IZRAZ;
import static semanal.NodeType.UNARNI_OPERATOR;
import static semanal.TerminalType.OP_DEC;
import static semanal.TerminalType.OP_INC;
import static semanal.types.NumberType.INT;

public class UnarniIzraz extends Node {

    public boolean leftAssignableExpression;
    public Type type;

    public UnarniIzraz(Node parent) {
        super(parent, UNARNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 54. str <--o
        o---------------o

        <unarni_izraz> ::=
            <postfiks_izraz>
            | OP_INC <unarni_izraz>
            | OP_DEC <unarni_izraz>
            | <unarni_operator> <cast_izraz>

         */

        if (hasNChildren(1)) {
            PostfiksIzraz postfiksIzraz = getChild(0);

            addNodeCheckToTasks(postfiksIzraz);
            addProcedureToTasks(() -> {
                type = postfiksIzraz.type;
                leftAssignableExpression = postfiksIzraz.leftAssignableExpression;
            });
        } else if (hasNChildren(2)) {

            if (isChildOfTerminalType(0, OP_INC) || isChildOfTerminalType(0, OP_DEC)) {
                TerminalNode terminalNode = getChild(0);
                UnarniIzraz unarniIzraz = getChild(1);

                addNodeCheckToTasks(unarniIzraz);

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });

                addProcedureToTasks(() -> {
                    unarniIzraz.leftAssignableExpression = true;
                    unarniIzraz.type = Type.createNumber(INT);
                });
            } else if (isChildOfType(0, UNARNI_OPERATOR)) {
                UnarniOperator unarniOperator = getChild(0);
                CastIzraz castIzraz = getChild(1);

                addNodeCheckToTasks(castIzraz);
                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });
                addProcedureToTasks(() -> castIzraz.type = Type.createNumber(INT));

            } else
                throw new IllegalStateException("Invalid syntax tree structure.");

        } else
            throw new IllegalStateException("Invalid syntax tree structure.");
    }
}
