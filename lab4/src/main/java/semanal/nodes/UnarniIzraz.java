package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.types.Type;
import semanal.variables.Variable;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.UNARNI_IZRAZ;
import static semanal.TerminalType.*;
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


//
//                    addProcedureToTasks(() -> {
////                        Variable variable=getVariableMemory().get(getChild(1).getChild())
//                        frisc.append("POP R0", whereTo());
//                        frisc.append(format("%s R0 , 1 , R0", terminalNode.getTerminalType() == OP_INC ? "ADD" : "SUB"), whereTo());
//
//                        frisc.append("STORE R0, ("+1+")",whereTo());
//                        frisc.append("PUSH R0", whereTo());
//                    });

                } else {
                    UnarniOperator unarniOperator = getChild(0);
                    CastIzraz castIzraz = getChild(1);

                    addNodeCheckToTasks(castIzraz);
                    addErrorCheckToTasks(() -> castIzraz.type.implicitConvertInto(INT));

                    addProcedureToTasks(() -> {
                        type = Type.createNumber(INT);
                        leftAssignableExpression = false;
                    });

                    if (unarniOperator.isChildOfTerminalType(0, MINUS)) {
                        tasks.add(() -> {
                            frisc.append("POP R1", whereTo());
                            frisc.append("MOVE 0, R1", whereTo());
                            frisc.append("SUB R1,R0,R0", whereTo());
                            frisc.append("PUSH R0", whereTo());
                            return TaskResult.success(this);
                        });
                    }
                }
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}