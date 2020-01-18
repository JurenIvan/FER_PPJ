package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.types.ArrayModel;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.PRIMARNI_IZRAZ;

public class PrimarniIzraz extends Node {

    public boolean leftAssignableExpression;
    public Type type;

    public PrimarniIzraz(Node parent) {
        super(parent, PRIMARNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 51. str <--o
        o---------------o

        <primarni_izraz> ::=
            IDN
            | BROJ
            | ZNAK
            | NIZ_ZNAKOVA
            | L_ZAGRADA <izraz> D_ZAGRADA
         */

        switch (getChildrenNumber()) {
            case 1: {
                TerminalNode firstChild = getChild(0);
                if (firstChild.getTerminalType() == TerminalType.IDN) {

                    tasks.add(() -> {
                        if (!getVariableMemory().checkGlobal(firstChild.getSourceCode())) {
                            return TaskResult.failure(this);
                        }
                        type = getVariableMemory().get(firstChild.getSourceCode());
                        if (type.getSubType() == SubType.ARRAY) {
                            type = Type.createArrayFromIDN(type);
                        }
                        leftAssignableExpression = type.isLeftAssignable();
                        return TaskResult.success(this);
                    });
                    //same as:
                    // addErrorCheckToTasks(() -> check_if_variable_in_scope);
                    // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

                } else if (firstChild.getTerminalType() == TerminalType.BROJ || firstChild.getTerminalType() == TerminalType.ZNAK) {

                    NumberType numberType;
                    if (firstChild.getTerminalType() == TerminalType.BROJ) {
                        numberType = NumberType.INT;
                        tasks.add(() -> {
                            int number = Integer.parseInt(firstChild.getSourceCode());
                            if (number < Math.pow(2, 16)) {
                                friscCodeAppender.appendCommand("MOVE %D " + number + ", R0");
                                friscCodeAppender.appendCommand("PUSH R0");
                                return TaskResult.success(this);
                            }
                            String labela = friscCodeAppender.appendConstant(number);
                            friscCodeAppender.appendCommand(format("LOAD R0, (%s)", labela));
                            friscCodeAppender.appendCommand("PUSH R0");
                            return TaskResult.success(this);
                        });
                    } else {
                        numberType = NumberType.CHAR;
                    }
                    Type number = Type.createNumber(numberType);

                    tasks.add(() -> {
                        if (!number.getNumber().checkIfValidValue(firstChild.getSourceCode())) {
                            return TaskResult.failure(this);
                        }
                        type = number;
                        leftAssignableExpression = false;
                        return TaskResult.success(this);
                    });
                    //same as:
                    // addErrorCheckToTasks(number.getNumber().checkIfValidValue(firstChild.getSourceCode()));
                    // addProcedureToTasks(()->{ type = number; leftAssignableExpression = false; });

                } else if (firstChild.getTerminalType() == TerminalType.NIZ_ZNAKOVA) {

                    // TODO is the length check needed?
                    Type array = Type.createArrayFromString(NumberType.CONST_CHAR,
                            ArrayModel.getNumberOfCharElementsOfString(firstChild.getSourceCode()));

                    tasks.add(() -> {
                        if (!ArrayModel.isValidCharArray(firstChild.getSourceCode())) {
                            return TaskResult.failure(this);
                        }
                        type = array;
                        leftAssignableExpression = false;
                        return TaskResult.success(this);
                    });
                    // same as:
                    // addErrorCheckToTasks(ArrayModel.isValidCharArray(firstChild.getSourceCode()));
                    // addProcedureToTasks(()->{type = array; leftAssignableExpression = false;});
                }
                break;
            }
            case 3: {
                Izraz izraz = getChild(1);

                tasks.add(() -> TaskResult.success(izraz)); // same as: addNodeCheckToTasks(izraz);

                tasks.add(() -> {
                    type = izraz.type;
                    leftAssignableExpression = izraz.leftAssignableExpression;
                    return TaskResult.success(this);
                }); // same as: addProcedureToTasks(()->{ type = izraz.type; leftAssignableExpression = izraz.leftAssignableExpression; });
                break;
            }
            default: {
                throw new IllegalStateException("Invalid syntax tree structure.");
            }
        }

    }
}
