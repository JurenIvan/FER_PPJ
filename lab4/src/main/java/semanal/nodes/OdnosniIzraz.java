package semanal.nodes;

import semanal.FriscCodeAppender;
import semanal.Node;
import semanal.TerminalType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.ODNOSNI_IZRAZ;
import static semanal.types.NumberType.INT;

public class OdnosniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public OdnosniIzraz(Node parent) {
        super(parent, ODNOSNI_IZRAZ);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 58. str <--o
        o---------------o

        <odnosni_izraz> ::=
			<aditivni_izraz>
			| <odnosni_izraz> OP_LT <aditivni_izraz>
			| <odnosni_izraz> OP_GT <aditivni_izraz>
			| <odnosni_izraz> OP_LTE <aditivni_izraz>
			| <odnosni_izraz> OP_GTE <aditivni_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                AditivniIzraz aditivniIzraz = getChild(0);

                addNodeCheckToTasks(aditivniIzraz);

                addProcedureToTasks(() -> {
                    type = aditivniIzraz.type;
                    leftAssignableExpression = aditivniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                OdnosniIzraz odnosniIzraz = getChild(0);
                TerminalType terminal = getChild(1).toTerminalNode().getTerminalType();
                AditivniIzraz aditivniIzraz = getChild(2);

                addNodeCheckToTasks(odnosniIzraz);
                addErrorCheckToTasks(() -> odnosniIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(aditivniIzraz);
                addErrorCheckToTasks(() -> aditivniIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });

                String labela = FriscCodeAppender.generateLabel();
                String labelaElse = FriscCodeAppender.generateLabel();

                addProcedureToTasks(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append("POP R1", whereTo());
                    frisc.append("CMP R1, R0", whereTo());

                    switch (terminal) {
                        case OP_LT:
                            frisc.append("JP_SLT " + labela, whereTo());
                            break;
                        case OP_GT:
                            frisc.append("JP_SGT " + labela, whereTo());
                            break;
                        case OP_LTE:
                            frisc.append("JP_SLE " + labela, whereTo());
                            break;
                        case OP_GTE:
                            frisc.append("JP_SGE " + labela, whereTo());
                            break;
                    }

                    frisc.append("MOVE 0, R0", whereTo());
                    frisc.append("JP " + labelaElse, whereTo());
                    frisc.append(labela, "MOVE 1, R0", whereTo());
                    frisc.append(labelaElse, "PUSH R0", whereTo());

                });

                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
