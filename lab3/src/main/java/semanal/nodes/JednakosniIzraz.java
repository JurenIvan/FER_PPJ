package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.JEDNAKOSNI_IZRAZ;
import static semanal.types.NumberType.INT;

public class JednakosniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public JednakosniIzraz(Node parent) {
        super(parent, JEDNAKOSNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 58. str <--o
        o---------------o

        <jednakosni_izraz> ::=
			<odnosni_izraz>
			| <jednakosni_izraz> OP_EQ <odnosni_izraz>
			| <jednakosni_izraz> OP_NEQ <odnosni_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                OdnosniIzraz odnosniIzraz = getChild(0);

                addNodeCheckToTasks(odnosniIzraz);

                addProcedureToTasks(() -> {
                    type = odnosniIzraz.type;
                    leftAssignableExpression = odnosniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                JednakosniIzraz jednakosniIzraz = getChild(0);
                OdnosniIzraz odnosniIzraz = getChild(2);

                addNodeCheckToTasks(jednakosniIzraz);
                addErrorCheckToTasks(() -> jednakosniIzraz.type.getNumber().implicitConvertInto(INT));
                addNodeCheckToTasks(odnosniIzraz);
                addErrorCheckToTasks(() -> odnosniIzraz.type.getNumber().implicitConvertInto(INT));

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
