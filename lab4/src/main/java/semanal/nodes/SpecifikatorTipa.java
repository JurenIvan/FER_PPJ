package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.SPECIFIKATOR_TIPA;
import static semanal.types.NumberType.CHAR;
import static semanal.types.NumberType.INT;

public class SpecifikatorTipa extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public SpecifikatorTipa(Node parent) {
        super(parent, SPECIFIKATOR_TIPA);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 56. str <--o
        o---------------o

        <specifikator_tipa> ::=
            KR_VOID
            | KR_CHAR
            | KR_INT

         */

        TerminalNode terminalNode = getChild(0);
        switch (terminalNode.getTerminalType()) {
            case KR_VOID:
                addProcedureToTasks(() -> type = Type.VOID_TYPE);
                break;
            case KR_CHAR:
                addProcedureToTasks(() -> type = Type.createNumber(CHAR));
                break;
            case KR_INT:
                addProcedureToTasks(() -> type = Type.createNumber(INT));
                break;
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
