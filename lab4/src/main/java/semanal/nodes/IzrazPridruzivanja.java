package semanal.nodes;

import semanal.Node;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.VariableResult;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.IZRAZ_PRIDRUZIVANJA;
import static semanal.variables.VariableType.HEAP_ELEMENT;

public class IzrazPridruzivanja extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public IzrazPridruzivanja(Node parent) {
        super(parent, IZRAZ_PRIDRUZIVANJA);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 61. str <--o
        o---------------o

        <izraz_pridruzivanja> ::=
			<log_ili_izraz>
			| <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>

         */

        switch (getChildrenNumber()) {
            case 1: {
                LogIliIzraz logIliIzraz = getChild(0);

                addNodeCheckToTasks(logIliIzraz);
                addProcedureToTasks(() -> {
                    type = logIliIzraz.type;
                    leftAssignableExpression = logIliIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                PostfiksIzraz postfiksIzraz = getChild(0);
                IzrazPridruzivanja izrazPridruzivanja = getChild(2);

                addNodeCheckToTasks(postfiksIzraz);
                addErrorCheckToTasks(() -> postfiksIzraz.leftAssignableExpression);
                addProcedureToTasks(() -> frisc.append("POP R0", whereTo()));
                addNodeCheckToTasks(izrazPridruzivanja);
                addErrorCheckToTasks(() -> izrazPridruzivanja.type.implicitConvertInto(postfiksIzraz.type));

                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type;
                    leftAssignableExpression = false;
                });


                addProcedureToTasks(() -> {
                    String name;
                    try {
                        name = postfiksIzraz.getChild(0).getChild(0).toTerminalNode().getSourceCode();


                        VariableResult result = getVariableMemory().get(name);
                        if (type.getSubType() == SubType.NUMBER) {
                            frisc.append("POP R0", whereTo());
                            if (result.getVariableType() == HEAP_ELEMENT) {
                                frisc.append(format("STORE R0, (R5 - %%D %d)", result.getPositionInBytes()), whereTo());
                            } else {
                                frisc.append(format("STORE R0, (%s)", result.getLabelName()), whereTo());
                            }
                        } else {
                            int count = result.getElementType().getArray().getNumberOfElements();
                            for (int i = count - 1; i >= 0; i--) {

                                frisc.append("POP R0", whereTo());
                                if (result.getVariableType() == HEAP_ELEMENT) {
                                    frisc.append(format("STORE R0, (R5 - %%D %d)", result.getPositionInBytes() + i * 4), whereTo());
                                } else {
                                    frisc.append(format("MOVE %s , R1", result.getLabelName()), whereTo());
                                    frisc.append(format("STORE R0, (R1 + %%D %d)", i * 4), whereTo());
                                }
                                frisc.append("", whereTo());
                            }
                        }
                        frisc.append("", whereTo());

                    } catch (ClassCastException ignored) {
                        name = postfiksIzraz.getChild(0).getChild(0).getChild(0).toTerminalNode().getSourceCode();
                        VariableResult result = getVariableMemory().get(name);
                        if (type.getSubType() == SubType.NUMBER) {
                            throw new IllegalStateException();
                        } else {
                            frisc.append("POP R2", whereTo());
                            frisc.append("ADD R2,R2,R2", whereTo());
                            frisc.append("ADD R2,R2,R2", whereTo());

                            frisc.append("POP R0", whereTo());
                            if (result.getVariableType() == HEAP_ELEMENT) {
                                frisc.append("SUB R5,R2,R2", whereTo());
                                frisc.append(format("STORE R0, (R2 - %%D %d)", result.getPositionInBytes()), whereTo());
                            } else {
                                frisc.append(format("MOVE %s , R1", result.getLabelName()), whereTo());
                                frisc.append("ADD R1,R2,R1", whereTo());
                                frisc.append("STORE R0, (R1)", whereTo());
                            }
                            frisc.append("", whereTo());

                        }
                        frisc.append("", whereTo());


                    }


                });

                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
