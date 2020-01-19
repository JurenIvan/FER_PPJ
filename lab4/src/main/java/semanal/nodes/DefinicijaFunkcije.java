package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.Utils;
import semanal.types.FunctionModel;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.Variable;

import java.util.ArrayList;
import java.util.Collections;

import static semanal.NodeType.DEFINICIJA_FUNKCIJE;
import static semanal.NodeType.TERMINAL;
import static semanal.WhereTo.MAIN;

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

        tasks.add(() -> {
            frisc.append(((TerminalNode) getChild(1)).getSourceCode(), "", MAIN);
            return TaskResult.success(this);
        });

        if (hasNChildren(6)) {
            ImeTipa imeTipa = getChild(0);
            TerminalNode idn = getChild(1);
            SlozenaNaredba slozenaNaredba = getChild(5);

            // 1.
            addNodeCheckToTasks(imeTipa);
            // 2.
            addErrorCheckToTasks(() -> FunctionModel.validReturnType(imeTipa.type));

            String functionName = idn.getSourceCode();

            if (isChildOfType(3, TERMINAL)) {

                // 3. 4. 5.
                addErrorCheckToTasks(() -> {
                    if (getVariableMemory().check(functionName)) {
                        Type functionType = getVariableMemory().get(functionName).getElementType();
                        if (functionType.getSubType() != SubType.FUNCTION)
                            return false;

                        FunctionModel functionModel = functionType.getFunction();
                        if (functionModel.isDefined())
                            return false;
                        if (!functionModel.acceptsVoid())
                            return false;
                        if (!functionModel.getReturnValueType().equals(imeTipa.type))
                            return false;
                    }

                    Type functionType = Type.createFunctionDefinition(Utils.listOf(Type.VOID_TYPE), Collections.emptyList(), imeTipa.type);
                    function = functionType.getFunction();
                    if (getVariableMemory().check(functionName)) {
                        getVariableMemory().set(Variable.AsFunction(functionName, functionType));
                    } else {
                        getVariableMemory().define(Variable.AsFunction(functionName, functionType));
                    }
                    return true;
                });

                // 6.
                addNodeCheckToTasks(slozenaNaredba);

            } else {

                ListaParametara listaParametara = getChild(3);

                // 3.
                addErrorCheckToTasks(() -> {
                    if (getVariableMemory().check(functionName)) {
                        Type functionType = getVariableMemory().get(functionName).getElementType();
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
                        FunctionModel functionModel = getVariableMemory().get(functionName).getElementType().getFunction();

                        if (!functionModel.getReturnValueType().equals(imeTipa.type))
                            return false;
                        if (!functionModel.parameterCheck(listaParametara.types, listaParametara.names))
                            return false;
                    }
                    return true;
                });
                // 6.
                addErrorCheckToTasks(() -> {
                    Type functionType;
                    try {
                        functionType = Type.createFunctionDefinition(listaParametara.types, listaParametara.names, imeTipa.type);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                    if (getVariableMemory().check(functionName)) {
                        getVariableMemory().set(Variable.AsFunction(functionName, functionType));
                    } else {
                        getVariableMemory().define(Variable.AsFunction(functionName, functionType));
                    }
                    function = functionType.getFunction();
                    return true;
                });
                // 7.
                addProcedureToTasks(() -> {
                    createLocalVariableMemory();
                    slozenaNaredba.createLocalScope = false;
                    for (int i = listaParametara.types.size() - 1; i >= 0; i--) {
                        Variable var = Variable.AsHeapElement(listaParametara.names.get(i), listaParametara.types.get(i), listaParametara.types.get(i).getSize());
                        getVariableMemory().define(var);
                    }
                });
                addNodeCheckToTasks(slozenaNaredba);

            }
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
