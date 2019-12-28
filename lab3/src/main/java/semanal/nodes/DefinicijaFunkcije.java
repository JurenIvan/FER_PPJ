package semanal.nodes;

import semanal.Node;
import semanal.types.FunctionModel;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static semanal.NodeType.DEFINICIJA_FUNKCIJE;
import static semanal.NodeType.TERMINAL;

public class DefinicijaFunkcije extends Node {

    public FunctionModel function;

    public DefinicijaFunkcije(Node parent) {
        super(parent, DEFINICIJA_FUNKCIJE);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();
        /*
        o---------------o
        o--> 66. str <--o
        o---------------o

        <definicija_funkcije> ::=
			<ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
			| <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA <slozena_naredba>

         */

        if (hasNChildren(6)) {
            ImeTipa imeTipa = getChild(0);
            TerminalNode idn = getChild(1);
            SlozenaNaredba slozenaNaredba = getChild(5);

            // 1.
            addNodeCheckToTasks(imeTipa);
            // 2.
            addErrorCheckToTasks(() -> imeTipa.type.getSubType() == SubType.NUMBER && !imeTipa.type.getNumber().isConst());

            String functionName = idn.getSourceCode();

            if (isChildOfType(3, TERMINAL)) {

                // 3. 4. 5.
                addErrorCheckToTasks(() -> {
                    if (getVariableMemory().check(functionName)) {
                        Type functionType = getVariableMemory().get(functionName);
                        if (functionType.getSubType() != SubType.FUNCTION)
                            return false;

                        FunctionModel functionModel = functionType.getFunction();
                        if (functionModel.isDefined())
                            return false;
                        if (!functionModel.acceptsVoid())
                            return false;
                        if (!functionModel.getReturnValueType().equals(imeTipa))
                            return false;

                        return true;
                    }

                    try {
                        // TODO should a new function variable be created (as is done)
                        //      or rather modify the existing, already fetched function variable?
                        Type functionType = Type.createFunctionDeclaration(List.of(Type.VOID_TYPE), Collections.emptyList(),
                                imeTipa.type); // TODO List.of since java 9, is that OK?

                        function = functionType.getFunction();
                        getVariableMemory().define(functionName, functionType);
                        return true;
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                });

                // 6.
                addNodeCheckToTasks(slozenaNaredba);

            } else {

                ListaParametara listaParametara = getChild(3);

                // 3.
                addErrorCheckToTasks(() -> {
                    if (getVariableMemory().check(functionName)) {
                        Type functionType = getVariableMemory().get(functionName);
                        if (functionType.getSubType() != SubType.FUNCTION)
                            return false;

                        FunctionModel functionModel = functionType.getFunction();
                        if (functionModel.isDefined())
                            return false;
                    }
                    return true;
                });
                // 4.
                addNodeCheckToTasks(listaParametara);
                // 5.
                addErrorCheckToTasks(() -> {
                    if (getVariableMemory().check(functionName)) {
                        FunctionModel functionModel = getVariableMemory().get(functionName).getFunction();

                        if (!functionModel.getReturnValueType().equals(imeTipa.type))
                            return false;
                        if (functionModel.parameterCheck(listaParametara.types, listaParametara.names))
                            return false;
                    }
                    return true;
                });
                // 6.
                addProcedureToTasks(() -> {
                    createLocalVariableMemory();

                    // TODO should a new function variable be created (as is done)
                    //      or rather modify the existing, already fetched function variable?
                    Type functionType = Type.createFunctionDefinition(listaParametara.types, listaParametara.names, imeTipa.type);
                    getVariableMemory().define(functionName, functionType);
                    function = functionType.getFunction();
                });
                // 7.
                addProcedureToTasks(() -> {
                    for (int i = 0; i < listaParametara.types.size(); i++) {
                        getVariableMemory().define(listaParametara.names.get(i), listaParametara.types.get(i));
                    }
                });

            }
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
