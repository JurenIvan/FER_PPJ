package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.types.ArrayModel;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.VariableResult;

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
                        switch (result.getVariableType()) {
                            case LABEL_ELEMENT: {
                                if (result.getElementType().getSubType() == SubType.NUMBER) {
                                    frisc.append(format("LOAD R0, (%s)", result.getLabelName()), whereTo());
                                    frisc.append("PUSH R0", whereTo());
                                } else {
                                    int count = result.getElementType().getArray().getNumberOfElements();
                                    for (int i = count - 1; i >= 0; i--) {
                                        frisc.append(format("MOVE %s, R1", result.getLabelName()), whereTo());
                                        frisc.append(format("LOAD R0, (R1 + %%D %d)", 4 * i), whereTo());
                                        frisc.append("PUSH R0", whereTo());
                                    }
                                }
                                break;
                            }
                            case HEAP_ELEMENT: {
                                if (result.getElementType().getSubType() == SubType.NUMBER) {
                                    frisc.append(format("LOAD R0, (R5 - %%D %d)", result.getPositionInBytes()), whereTo());
                                    frisc.append("PUSH R0", whereTo());
                                } else {
                                    int count = result.getElementType().getArray().getNumberOfElements();
                                    for (int i = count - 1; i >= 0; i--) {
                                        frisc.append(format("LOAD R0, (R5 - %%D %d)", 4 * i + result.getPositionInBytes()), whereTo());
                                        frisc.append("PUSH R0", whereTo());
                                    }
                                }
                                break;
                            }
                            case FUNCTION: {
                                // throw new IllegalStateException();
                            }
                        }
                    });

                } else if (firstChild.getTerminalType() == TerminalType.BROJ || firstChild.getTerminalType() == TerminalType.ZNAK) {
                    addErrorCheckToTasks(() -> {
                        NumberType numberType;
                        int value = -99999999;
                        if (firstChild.getTerminalType() == TerminalType.BROJ) {
                            numberType = NumberType.INT;
                            try {
                                value = Integer.parseInt(firstChild.getSourceCode());
                            } catch (NumberFormatException ignored) {
                            }
                        } else {
                            numberType = NumberType.CHAR;
                            try {
                                value = firstChild.getSourceCode().charAt(1);
                            } catch (NumberFormatException ignored) {
                            }
                        }

                        if (value < Math.pow(2, 16)) {
                            frisc.append(format("MOVE %%D %d, R0", value), whereTo());
                            frisc.append("PUSH R0", whereTo());
                        } else {
                            String label = frisc.appendConstant(value);
                            frisc.append(format("LOAD R0, (%s)", label), whereTo());
                            frisc.append("PUSH R0", whereTo());
                        }
                        frisc.append("", whereTo());

                        Type number = Type.createNumber(numberType);
                        if (!number.getNumber().checkIfValidValue(firstChild.getSourceCode())) {
                            return false;
                        }
                        type = number;
                        leftAssignableExpression = false;
                        return true;
                    });

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
                });
                break;
            }
            default: {
                throw new IllegalStateException("Invalid syntax tree structure.");
            }
        }

    }
}
