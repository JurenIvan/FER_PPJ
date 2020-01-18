package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_IZRAZ;
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

        switch (getChildrenNumber()) {
            case 1: {
                PostfiksIzraz postfiksIzraz = getChild(0);

                addNodeCheckToTasks(postfiksIzraz);

                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type;
                    leftAssignableExpression = postfiksIzraz.leftAssignableExpression;
                });
                break;
            }
            case 2: {
                if (isChildOfTerminalType(0, OP_INC) || isChildOfTerminalType(0, OP_DEC)) {
                    TerminalNode terminalNode = getChild(0);
                    UnarniIzraz unarniIzraz = getChild(1);

                    addNodeCheckToTasks(unarniIzraz);
                    addErrorCheckToTasks(() -> unarniIzraz.leftAssignableExpression && unarniIzraz.type.implicitConvertInto(INT));

                    addProcedureToTasks(() -> {
                        type = Type.createNumber(INT);
                        leftAssignableExpression = false;
                    });
                    if (isChildOfTerminalType(0, OP_DEC)) {
                        tasks.add(() -> {
                            friscCodeAppender.appendCommand("POP R1");
                            friscCodeAppender.appendCommand("MOVE 0, R1");
                            friscCodeAppender.appendCommand("SUB R0,R1,R0");
                            friscCodeAppender.appendCommand("PUSH R0");
                            return TaskResult.success(this);
                        });
                    }
                } else {
                    CastIzraz castIzraz = getChild(1);

                    addNodeCheckToTasks(castIzraz);
                    addErrorCheckToTasks(() -> castIzraz.type.implicitConvertInto(INT));

                    addProcedureToTasks(() -> {
                        type = Type.createNumber(INT);
                        leftAssignableExpression = false;
                    });
                }
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}