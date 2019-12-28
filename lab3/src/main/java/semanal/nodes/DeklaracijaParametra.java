package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA_PARAMETRA;

public class DeklaracijaParametra extends Node {

    public Type type;
    public String name;

    public DeklaracijaParametra(Node parent) {
        super(parent, DEKLARACIJA_PARAMETRA);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        switch (getChildrenNumber()) {
            case 2: {
                ImeTipa imeTipa = getChild(0);

                addNodeCheckToTasks(imeTipa);
                addErrorCheckToTasks(() -> !imeTipa.type.equals(Type.createVoid()));

                addProcedureToTasks(() -> {
                    type = imeTipa.type;
                    name = imeTipa.name;
                });
                break;
            }
            case 4: {
                ImeTipa imeTipa = getChild(0);
                TerminalNode terminalNode = getChild(1);

                addNodeCheckToTasks(imeTipa);
                addErrorCheckToTasks(() -> !imeTipa.type.equals(Type.createVoid()));

                addProcedureToTasks(() -> {
                    type = Type.createArray(imeTipa.type.getNumber());
                    name = terminalNode.getSourceCode(); //todo check
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
