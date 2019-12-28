package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.DEFINICIJA_FUNKCIJE;

public class DefinicijaFunkcije extends Node {

    public DefinicijaFunkcije(Node parent) {
        super(parent, DEFINICIJA_FUNKCIJE);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        if (getChildrenNumber() != 5)
            throw new IllegalStateException("Invalid syntax tree structure.");

        ImeTipa imeTipa = getChild(0);
        TerminalNode terminalNode = getChild(1);
        SlozenaNaredba slozenaNaredba = getChild(4);

        addNodeCheckToTasks(imeTipa);
        addErrorCheckToTasks(() -> !imeTipa.type.getNumber().isConst());

        addErrorCheckToTasks(()->getVariableMemory().check(terminalNode.getSourceCode()));  //terminalNode.getSourceCode() ??????
        addProcedureToTasks(() -> {
            if(getVariableMemory().check(terminalNode.getSourceCode())){
   //             getVariableMemory().get(terminalNode.getSourceCode());//todo ???
            }
        });  //todo
   //     addProcedureToTasks(() -> getVariableMemory().set(terminalNode.getSourceCode(),terminalNode.getTerminalType()));  //todo

        //
        addNodeCheckToTasks(slozenaNaredba);
    }
}
