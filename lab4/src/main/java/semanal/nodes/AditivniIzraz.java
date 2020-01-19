package semanal.nodes;

import semanal.Node;
import semanal.TerminalType;
import semanal.WhereTo;
import semanal.types.Type;

import java.util.ArrayList;

import static java.lang.String.format;
import static semanal.NodeType.ADITIVNI_IZRAZ;
import static semanal.TerminalType.PLUS;
import static semanal.WhereTo.INIT;
import static semanal.WhereTo.MAIN;
import static semanal.types.NumberType.INT;

public class AditivniIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public AditivniIzraz(Node parent) {
        super(parent, ADITIVNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 57. str <--o
        o---------------o

        <aditivni_izraz> ::=
			<multiplikativni_izraz>
			| <aditivni_izraz> PLUS <multiplikativni_izraz>
			| <aditivni_izraz> MINUS <multiplikativni_izraz>

         */
        switch (getChildrenNumber()) {
            case 1: {
                MultiplikativniIzraz multiplikativniIzraz = getChild(0);

                addNodeCheckToTasks(multiplikativniIzraz);

                addProcedureToTasks(() -> {
                    type = multiplikativniIzraz.type;
                    leftAssignableExpression = multiplikativniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 3: {
                AditivniIzraz aditivniIzraz = getChild(0);
                TerminalType terminal = getChild(1).toTerminalNode().getTerminalType();
                MultiplikativniIzraz multiplikativniIzraz = getChild(2);

                addNodeCheckToTasks(aditivniIzraz);
                addErrorCheckToTasks(() -> aditivniIzraz.type.implicitConvertInto(INT));
                addNodeCheckToTasks(multiplikativniIzraz);
                addErrorCheckToTasks(() -> multiplikativniIzraz.type.implicitConvertInto(INT));

                addProcedureToTasks(() -> {
                    type = Type.createNumber(INT);
                    leftAssignableExpression = false;
                });

                addProcedureToTasks(()->{
                    WhereTo whereTo = getVariableMemory().isGlobal()? INIT: MAIN;
                    frisc.append("POP R0", whereTo);
                    frisc.append("POP R1", whereTo);
                    frisc.append(format("%s R1, R0, R0", terminal==PLUS?"ADD":"SUB"), whereTo);
                    frisc.append("PUSH R0", whereTo);
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}



