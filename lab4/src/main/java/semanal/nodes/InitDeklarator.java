package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.VariableResult;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public Type nType;
    public Type type;

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    /*
    o---------------o
    o--> 69. str <--o
    o---------------o

     <init_deklarator> ::=
			<izravni_deklarator>
			| <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

     */

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        switch (getChildrenNumber()) {
            case 1: {
                IzravniDeklarator izravniDeklarator = getChild(0);

                // 1.
                addProcedureToTasks(() -> izravniDeklarator.nType = nType);
                addNodeCheckToTasks(izravniDeklarator);
                // 2.
                addErrorCheckToTasks(
                        () -> izravniDeklarator.type.getSubType() != SubType.NUMBER || izravniDeklarator.type.getNumber().isNotConst());
                addErrorCheckToTasks(
                        () -> izravniDeklarator.type.getSubType() != SubType.ARRAY || izravniDeklarator.type.getArray().getNumberType()
                                .isNotConst());

                break;
            }
            case 3: {
                IzravniDeklarator izravniDeklarator = getChild(0);
                Inicijalizator inicijalizator = getChild(2);

                // 1. && 2.
                addProcedureToTasks(() -> izravniDeklarator.nType = nType);
                addNodeCheckToTasks(inicijalizator);
                addNodeCheckToTasks(izravniDeklarator);
                // 3.
                addErrorCheckToTasks(() -> {
                    if (izravniDeklarator.type.getSubType() == SubType.NUMBER) {
                        if (inicijalizator.type == null) {
                            return false;
                        }
                        return inicijalizator.type.implicitConvertInto(izravniDeklarator.type.getNumber().toNonConst());
                    } else if (izravniDeklarator.type.getSubType() == SubType.ARRAY) {
                        if (inicijalizator.types == null || inicijalizator.types.isEmpty())
                            return false;

                        if (izravniDeklarator.type.getArray().getNumberOfElements() < inicijalizator.numberOfElements)
                            return false;

                        if (inicijalizator.type != null && inicijalizator.type.getArray().isCopiedFromIDN()
                                && inicijalizator.numberOfElements != izravniDeklarator.type.getArray().getNumberOfElements())
                            return false;

                        if (inicijalizator.type != null && inicijalizator.type.getArray().getNumberType().isNotConst())
                            return false;

                        NumberType arrayNumberType = izravniDeklarator.type.getArray().getNumberType().toNonConst();
                        for (Type type : inicijalizator.types) {
                            if (!type.implicitConvertInto(arrayNumberType))
                                return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                });

                tasks.add(() -> {
                    VariableResult result = getVariableMemory().get(izravniDeklarator.getChild(0).toTerminalNode().getSourceCode());
                    switch (result.getVariableType()) {
                        case LABEL_ELEMENT: {
                            if (nType.getSubType() == SubType.NUMBER) {
                                frisc.append("POP R0", whereTo());
                                frisc.append(format("STORE R0, (%s)", result.getLabelName()), whereTo());
                            } else {
                                int count = result.getElementType().getArray().getNumberOfElements();
                                frisc.append(format("MOVE %s, R1", result.getLabelName()), whereTo());
                                for (int i = count - 1; i >= 0; i--) {
                                    frisc.append("POP R0", whereTo());
                                    frisc.append(format("STORE R0, (R1 - %d)", result.getPositionInBytes() + 4 * i), whereTo());
                                    frisc.append("", whereTo());
                                }
                            }
                            frisc.append("", whereTo());
                            break;
                        }
                        case HEAP_ELEMENT: {
                            if (nType.getSubType() == SubType.NUMBER) {
                                frisc.append("POP R0", whereTo());
                                frisc.append("ADD R5, 4, R5", whereTo());
                                frisc.append(format("STORE R0, (R5 - %%D %d)", result.getPositionInBytes()), whereTo());
                            } else {
                                int count = result.getElementType().getArray().getNumberOfElements();
                                for (int i = count - 1; i >= 0; i--) {
                                    frisc.append("POP R0", whereTo());
                                    frisc.append("ADD R5, 4, R5", whereTo());
                                    frisc.append(format("STORE R0, (R5 - %%D %d)", 4 * i + result.getPositionInBytes()), whereTo());
                                    frisc.append("", whereTo());
                                }
                            }
                            frisc.append("", whereTo());
                            frisc.append("", whereTo());
                            break;
                        }
                        case FUNCTION: {
                            // do nothing
                            break;
                        }
                    }
                    return TaskResult.success(this);
                });

                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
