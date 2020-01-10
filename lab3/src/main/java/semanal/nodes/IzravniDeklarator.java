package semanal.nodes;

import semanal.Node;
import semanal.TerminalType;
import semanal.Utils;
import semanal.types.ArrayModel;
import semanal.types.FunctionModel;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.Collections;

import static semanal.NodeType.IZRAVNI_DEKLARATOR;

public class IzravniDeklarator extends Node {

    public Type type;
    public Type nType;
    private Integer numberOfElements;

    public IzravniDeklarator(Node parent) {
        super(parent, IZRAVNI_DEKLARATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 69. str <--o
        o---------------o

        <izravni_deklarator> ::=
			IDN
			| IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
			| IDN L_ZAGRADA KR_VOID D_ZAGRADA
			| IDN L_ZAGRADA <lista_parametara> D_ZAGRADA

         */

        switch (getChildrenNumber()) {
            case 1: {
                TerminalNode idn = getChild(0);
                String functionName = idn.getSourceCode();

                // 1.
                addErrorCheckToTasks(() -> !Type.VOID_TYPE.equals(nType));
                // 2.
                addErrorCheckToTasks(() -> !getVariableMemory().checkLocal(functionName));
                // 3. & post processing
                addProcedureToTasks(() -> {
                    type = nType;
                    getVariableMemory().define(idn.getSourceCode(), nType);
                });

                break;
            }
            case 4: {
                TerminalNode idn = getChild(0);
                String functionName = idn.getSourceCode();

                if (isChildOfTerminalType(2, TerminalType.BROJ)) {
                    TerminalNode broj = getChild(2);

                    // 1.
                    addErrorCheckToTasks(() -> !Type.VOID_TYPE.equals(nType));
                    // 2.
                    addErrorCheckToTasks(() -> !getVariableMemory().checkLocal(functionName));
                    // 3.
                    addErrorCheckToTasks(() -> ArrayModel.isValidArraySize(broj.getSourceCode()));
                    // 4. & post processing
                    addErrorCheckToTasks(() -> nType.getSubType() == SubType.NUMBER);
                    addProcedureToTasks(() -> {
                        numberOfElements = Integer.parseInt(broj.getSourceCode());
                        type = Type.createArray(nType.getNumber(), numberOfElements);
                        getVariableMemory().define(functionName, type);
                    });

                } else if (isChildOfTerminalType(2, TerminalType.KR_VOID)) {

                    // 1. & 2. & post processing
                    addErrorCheckToTasks(() -> {
                        if (!getVariableMemory().checkLocal(functionName)) {
                            try {
                                type = Type.createFunctionDeclaration(Utils.listOf(Type.VOID_TYPE), Collections.emptyList(), nType);
                            } catch (IllegalArgumentException ex) {
                                return false;
                            }
                            getVariableMemory().define(functionName, type);

                            return true;
                        } else {
                            Type functionType = getVariableMemory().get(functionName);
                            type = functionType;
                            if (functionType.getSubType() == SubType.FUNCTION)
                                return false;

                            return functionType.getFunction().acceptsVoid();
                        }
                    });

                } else {
                    ListaParametara listaParametara = getChild(2);

                    // 1.
                    addNodeCheckToTasks(listaParametara);
                    // 2. & 3. & post processing
                    addErrorCheckToTasks(() -> {
                        if (getVariableMemory().checkLocal(functionName)) {
                            type = getVariableMemory().get(functionName);
                            if (type.getSubType() != SubType.FUNCTION)
                                return false;

                            FunctionModel functionModel = type.getFunction();
                            if (!functionModel.getReturnValueType().equals(nType))
                                return false;
                            if (!functionModel.parameterCheck(listaParametara.types, listaParametara.names))
                                return false;

                        } else {
                            try {
                                type = Type.createFunctionDeclaration(listaParametara.types, listaParametara.names, nType);
                            } catch (IllegalArgumentException ex) {
                                return false;
                            }
                            getVariableMemory().define(functionName, type);

                        }
                        return true;
                    });
                }
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
