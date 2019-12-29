package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.LOG_I_IZRAZ;
import static semanal.types.NumberType.INT;

public class LogIIzraz extends Node {


    public Type type;
    public boolean leftAssignableExpression;

    public LogIIzraz(Node parent) {
        super(parent, LOG_I_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <log_i_izraz> ::=
			<bin_ili_izraz>
			| <log_i_izraz> OP_I <bin_ili_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                BinIliIzraz binIliIzraz = getChild(0);

                addNodeCheckToTasks(binIliIzraz);

                addProcedureToTasks(() -> {
                    type = binIliIzraz.type;
                    leftAssignableExpression = binIliIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                LogIIzraz logIIzraz = getChild(0);
                BinIliIzraz binIliIzraz = getChild(2);

                addNodeCheckToTasks(logIIzraz);
                addErrorCheckToTasks(() -> logIIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(binIliIzraz);
                addErrorCheckToTasks(() -> binIliIzraz.type.implicitConvertInto(INT));

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
