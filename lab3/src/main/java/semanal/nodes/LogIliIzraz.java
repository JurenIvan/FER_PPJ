package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.LOG_ILI_IZRAZ;
import static semanal.types.NumberType.INT;

public class LogIliIzraz extends Node {

    public Type type;
    public Boolean leftAssignableExpression;

    public LogIliIzraz(Node parent) {
        super(parent, LOG_ILI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <log_ili_izraz> ::=
			<log_i_izraz>
			| <log_ili_izraz> OP_ILI <log_i_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                LogIIzraz logIIzraz = getChild(0);

                addNodeCheckToTasks(logIIzraz);

                addProcedureToTasks(() -> {
                    type = logIIzraz.type;
                    leftAssignableExpression = logIIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                LogIliIzraz logIliIzraz = getChild(0);
                LogIIzraz logIIzraz = getChild(2);

                addNodeCheckToTasks(logIliIzraz);
                addErrorCheckToTasks(() -> logIliIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(logIIzraz);
                addErrorCheckToTasks(() -> logIIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
