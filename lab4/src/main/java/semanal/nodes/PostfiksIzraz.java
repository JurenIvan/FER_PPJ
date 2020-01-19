package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.WhereTo;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.VariableResult;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.IZRAZ;
import static semanal.NodeType.POSTFIKS_IZRAZ;
import static semanal.TerminalType.OP_INC;

public class PostfiksIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public PostfiksIzraz(Node parent) {
        super(parent, POSTFIKS_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 52. str <--o
        o---------------o

        <postfiks_izraz> ::=
            <primarni_izraz>
            | <postfiks_izraz> OP_INC
            | <postfiks_izraz> OP_DEC
            | <postfiks_izraz> L_ZAGRADA D_ZAGRADA
            | <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
            | <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA

         */

        switch (getChildrenNumber()) {
            case 1: {
                PrimarniIzraz primarniIzraz = getChild(0);

                // 1.
                addNodeCheckToTasks(primarniIzraz);

                // final step
                addProcedureToTasks(() -> {
                    type = primarniIzraz.type;
                    leftAssignableExpression = primarniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 2: {
                PostfiksIzraz postfiksIzraz = getChild(0);
                TerminalNode terminalNode = getChild(1);

                // 1.
                addNodeCheckToTasks(postfiksIzraz);
                // 2.
                addErrorCheckToTasks(
                        () -> postfiksIzraz.leftAssignableExpression && NumberType.implicitConvertInto(postfiksIzraz.type, NumberType.INT));

                // final step
                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type;
                    leftAssignableExpression = false;
                });

                addProcedureToTasks(() -> {
//                    frisc.append("POP R0", whereTo());
//                    frisc.append(format("%s R0 , 1 , R0", terminalNode.getTerminalType() == OP_INC ? "ADD" : "SUB"), whereTo());
//                    frisc.append("PUSH R0", whereTo());
            //        frisc.append("STORE R0, ("+getVariableMemory().get(getChild(0).)+")");
                });

                break;
            }
            case 3: {
                PostfiksIzraz postfiksIzraz = getChild(0);

                // NOTE: It is not necessary to check whether first and second terminals are of
                // L_ZAGRADA and D_ZAGRADA types, because parser makes sure that is satisfied
                // and therefore it can be removed..

                // 1.
                addNodeCheckToTasks(postfiksIzraz);
                // 2.
                addErrorCheckToTasks(
                        () -> postfiksIzraz.type.getSubType() == SubType.FUNCTION && postfiksIzraz.type.getFunction().acceptsVoid());

                // final step
                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type.getFunction().getReturnValueType();
                    leftAssignableExpression = false;
                });

                tasks.add(() -> {
                    TerminalNode terminalNode = ((TerminalNode) ((PostfiksIzraz) getChild(0)).getChild(0).getChild(0));
                    VariableResult result = getVariableMemory().get(terminalNode.getSourceCode());
                    frisc.append("CALL " + terminalNode.getSourceCode(), WhereTo.MAIN);
//                    friscCodeAppender.append("POP R6", WhereTo.MAIN);
                    frisc.append("PUSH R6", WhereTo.MAIN);
                    return TaskResult.success(this);
                });
                break;
            }
            case 4: {
                PostfiksIzraz postfiksIzraz = getChild(0);

                if (isChildOfType(2, IZRAZ)) {
                    Izraz izraz = getChild(2);

                    // 1.
                    addNodeCheckToTasks(postfiksIzraz);
                    // 2.
                    addErrorCheckToTasks(() -> postfiksIzraz.type.getSubType() == SubType.ARRAY);
                    // 3.
                    addNodeCheckToTasks(izraz);
                    // 4.
                    addErrorCheckToTasks(() -> NumberType.implicitConvertInto(izraz.type, NumberType.INT));

                    // final step, after 1-4 tests
                    addProcedureToTasks(() -> {
                        type = postfiksIzraz.type.getArray().getElementType();
                        leftAssignableExpression = type.getNumber().isNotConst();
                    });

                    addProcedureToTasks(() -> {
                        frisc.append("POP R0", whereTo());
                        VariableResult var = getVariableMemory().get(postfiksIzraz.getChild(0).getChild(0).toTerminalNode().getSourceCode());
                    });

                } else {
                    ListaArgumenata listaArgumenata = getChild(2);

                    // 1.
                    addNodeCheckToTasks(postfiksIzraz);
                    // 2.
                    addNodeCheckToTasks(listaArgumenata);
                    // 3.
                    addErrorCheckToTasks(() -> postfiksIzraz.type.getSubType() == SubType.FUNCTION);
                    addErrorCheckToTasks(() -> postfiksIzraz.type.getFunction().argumentCheck(listaArgumenata.argumentTypes));

                    // final step
                    addProcedureToTasks(() -> {
                        type = postfiksIzraz.type.getFunction().getReturnValueType();
                        leftAssignableExpression = false;
                    });

                    addProcedureToTasks(() -> {
                        // First, prepare heap
                        for (Type type : listaArgumenata.argumentTypes) {
                            switch (type.getSubType()) {
                                case NUMBER:
                                case FUNCTION: {
                                    frisc.append("ADD R5, 4, R5", whereTo());
                                    frisc.append("POP R0", whereTo());
                                    frisc.append("STORE R0, (R5)", whereTo());
                                    break;
                                }
                                case ARRAY: {
                                    // TODO
                                    break;
                                }
                            }
//                            friscCodeAppender.append("POP R6", WhereTo.MAIN);
//                            friscCodeAppender.append("PUSH R6", WhereTo.MAIN);
                        }
                        frisc.append("", whereTo());


                        // Second, call the function
                        frisc.append("CALL " + ((TerminalNode) ((PostfiksIzraz) getChild(0)).getChild(0).getChild(0)).getSourceCode(), WhereTo.MAIN);

                        // Third, clean heap
                        listaArgumenata.argumentTypes.forEach(e -> {
//                            frisc.append("SUB R5, 4, R5", whereTo());
                        });
//                        for (Type type: listaArgumenata.argumentTypes) {
//                            switch (type.getSubType()) {
//                                case NUMBER:
//                                case FUNCTION: {
//                                    friscCodeAppender.append("SUB R5, 4, R5", whereTo());
//                                    break;
//                                }
//                                case ARRAY: {
//                                    // TODO
//                                    break;
//                                }
//                            }
//                        }
                        frisc.append("", whereTo());


                        // Fourth, store the result in R6
                        frisc.append("PUSH R6", whereTo());
                        frisc.append("", whereTo());


                    });
                }
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
