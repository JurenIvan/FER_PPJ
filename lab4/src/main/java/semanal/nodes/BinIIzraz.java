package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.BIN_I_IZRAZ;
import static semanal.types.NumberType.INT;

public class BinIIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public BinIIzraz(Node parent) {
        super(parent, BIN_I_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 59. str <--o
        o---------------o

        <bin_i_izraz> ::=
			<jednakosni_izraz>
			| <bin_i_izraz> OP_BIN_I <jednakosni_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                JednakosniIzraz jednakosniIzraz = getChild(0);

                addNodeCheckToTasks(jednakosniIzraz);

                addProcedureToTasks(() -> {
                    type = jednakosniIzraz.type;
                    leftAssignableExpression = jednakosniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                BinIIzraz binIIzraz = getChild(0);
                JednakosniIzraz jednakosniIzraz = getChild(2);

                addNodeCheckToTasks(binIIzraz);
                addErrorCheckToTasks(() -> binIIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(jednakosniIzraz);
                addErrorCheckToTasks(() -> jednakosniIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });

                addProcedureToTasks(() -> {
                    friscCodeAppender.append("POP R0", whereTo());
                    friscCodeAppender.append("POP R1", whereTo());
                    friscCodeAppender.append("AND R1, R0, R0", whereTo());
                    friscCodeAppender.append("PUSH R0", whereTo());
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
