package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.BIN_XILI_IZRAZ;
import static semanal.types.NumberType.INT;

public class BinXiliIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public BinXiliIzraz(Node parent) {
        super(parent, BIN_XILI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 59. str <--o
        o---------------o

        <bin_xili_izraz> ::=
			<bin_i_izraz>
			| <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                BinIIzraz binIIzraz = getChild(0);

                addNodeCheckToTasks(binIIzraz);

                addProcedureToTasks(() -> {
                    type = binIIzraz.type;
                    leftAssignableExpression = binIIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                BinXiliIzraz binXiliIzraz = getChild(0);
                BinIIzraz binIIzraz = getChild(2);

                addNodeCheckToTasks(binXiliIzraz);
                addErrorCheckToTasks(() -> binXiliIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(binIIzraz);
                addErrorCheckToTasks(() -> binIIzraz.type.implicitConvertInto(INT));

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
