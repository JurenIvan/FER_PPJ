package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.BIN_ILI_IZRAZ;
import static semanal.types.NumberType.INT;

public class BinIliIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public BinIliIzraz(Node parent) {
        super(parent, BIN_ILI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <bin_ili_izraz> ::=
			<bin_xili_izraz>
			| <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                BinXiliIzraz binXiliIzraz = getChild(0);

                addNodeCheckToTasks(binXiliIzraz);

                addProcedureToTasks(() -> {
                    type = binXiliIzraz.type;
                    leftAssignableExpression = binXiliIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                BinIliIzraz binIliIzraz = getChild(0);
                BinXiliIzraz binXiliIzraz = getChild(2);

                addNodeCheckToTasks(binIliIzraz);
                addErrorCheckToTasks(() -> binIliIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(binXiliIzraz);
                addErrorCheckToTasks(() -> binXiliIzraz.type.implicitConvertInto(INT));

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
