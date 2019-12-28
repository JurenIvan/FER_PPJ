package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.IME_TIPA;

public class ImeTipa extends Node {

    public Type type;
    public String name;
    public boolean leftAssignableExpression;

    public ImeTipa(Node parent) {
        super(parent, IME_TIPA);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 56. str <--o
        o---------------o

        <ime_tipa> ::=
            <specifikator_tipa>
            | KR_CONST <specifikator_tipa>

         */

        switch (getChildrenNumber()) {
            case 1: {
                SpecifikatorTipa specifikatorTipa = getChild(0);

                addNodeCheckToTasks(specifikatorTipa);
                addProcedureToTasks(() -> type = specifikatorTipa.type);
                break;
            }
            case 2: {
                SpecifikatorTipa specifikatorTipa = getChild(0);

                addNodeCheckToTasks(specifikatorTipa);
                addErrorCheckToTasks(() -> !specifikatorTipa.type.equals(Type.createVoid()));

                addProcedureToTasks(() -> type = specifikatorTipa.type);
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
