package semanal.nodes;

import semanal.FriscCodeAppender;
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

                String label= FriscCodeAppender.generateLabel();

                addProcedureToTasks(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append("AND R0,R0,R0", whereTo());
                    frisc.append("CMP R0,0", whereTo());
                    frisc.append("JP_NE "+label, whereTo());
                });


                addNodeCheckToTasks(logIIzraz);
                addErrorCheckToTasks(() -> logIIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append(label,"PUSH R0", whereTo());
                });

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
