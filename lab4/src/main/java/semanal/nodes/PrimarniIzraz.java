package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.WhereTo;
import semanal.types.ArrayModel;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.VariableResult;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.PRIMARNI_IZRAZ;
import static semanal.WhereTo.INIT;
import static semanal.WhereTo.MAIN;

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
                        if (!getVariableMemory().check(firstChild.getSourceCode())) {
                            return TaskResult.failure(this);
                        }
                        type = getVariableMemory().get(firstChild.getSourceCode()).getElementType();
                        if (type.getSubType() == SubType.ARRAY) {
                            type = Type.createArrayFromIDN(type);
                        }
                        leftAssignableExpression = type.isLeftAssignable();
                        return TaskResult.success(this);
                    });

                    addProcedureToTasks(() -> {
                        VariableResult result = getVariableMemory().get(firstChild.getSourceCode());
                        WhereTo whereTo = getVariableMemory().isGlobal() ? INIT : MAIN;
                        switch (result.getVariableType()) {
                            case LABEL_ELEMENT: {
                                if (result.getElementType().getSubType() == SubType.NUMBER) {
                                    friscCodeAppender.append(format("LOAD R0, (%s)", result.getLabelName()), whereTo);
                                    friscCodeAppender.append("PUSH R0", whereTo);
                                } else {
                                    int count = result.getElementType().getArray().getNumberOfElements();
                                    for (int i = count - 1; i >= 0; i--) {
                                        friscCodeAppender.append(format("LOAD R0, (%s + %d)", result.getLabelName(), 4 * i), whereTo);
                                        friscCodeAppender.append("PUSH R0", whereTo);
                                    }
                                }
                                break;
                            }
                            case HEAP_ELEMENT: {
                                if (result.getElementType().getSubType() == SubType.NUMBER) {
                                    friscCodeAppender.append(format("LOAD R0, (R5 + %%D %d)", result.getPosition()), whereTo);
                                    friscCodeAppender.append("PUSH R0", whereTo);
                                } else {
                                    int count = result.getElementType().getArray().getNumberOfElements();
                                    for (int i = count - 1; i >= 0; i--) {
                                        friscCodeAppender.append(format("LOAD R0, (R5 - %%D %d)", 4 * i), whereTo);
                                        friscCodeAppender.append("PUSH R0", whereTo);
                                    }
                                }
                                break;
                            }
                            case FUNCTION: {
                                // throw new IllegalStateException();
                            }
                        }
                    });
                    //same as:
                    // addErrorCheckToTasks(() -> check_if_variable_in_scope);
                    // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

                } else if (firstChild.getTerminalType() == TerminalType.BROJ || firstChild.getTerminalType() == TerminalType.ZNAK) {

                    NumberType numberType;
                    if (firstChild.getTerminalType() == TerminalType.BROJ) {
                        numberType = NumberType.INT;
                        tasks.add(() -> {
                            int number = -99999999;
                            try {
                                number = Integer.parseInt(firstChild.getSourceCode());
                            } catch (NumberFormatException ignored) {
                            }
                            if (number < Math.pow(2, 16)) {
                                if (getVariableMemory().isGlobal()) {
                                    friscCodeAppender.append(format("MOVE %%D %d, R0", number), INIT);
                                    friscCodeAppender.append("PUSH R0", INIT);
                                } else {
                                    friscCodeAppender.append(format("MOVE %%D %d, R0", number), WhereTo.MAIN);
                                    friscCodeAppender.append("PUSH R0", WhereTo.MAIN);
                                }
                                return TaskResult.success(this);
                            }
                            String labela = friscCodeAppender.appendConstant(number);
                            if (getVariableMemory().isGlobal()) {
                                friscCodeAppender.append(format("LOAD R0, (%s)", labela), INIT);
                                friscCodeAppender.append("PUSH R0", INIT);
                            } else {
                                friscCodeAppender.append(format("LOAD R0, (%s)", labela), WhereTo.MAIN);
                                friscCodeAppender.append("PUSH R0", WhereTo.MAIN);
                            }
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
