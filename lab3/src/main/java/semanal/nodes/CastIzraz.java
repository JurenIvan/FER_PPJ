package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.CAST_IZRAZ;

public class CastIzraz extends Node {

    public Type type;
    public boolean leftAssignableExpression;

    public CastIzraz(Node parent) {
        super(parent, CAST_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 55. str <--o
        o---------------o

        <cast_izraz> ::=
            <unarni_izraz>
            | L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>

         */

        switch (getChildrenNumber()) {
            case 1: {
                UnarniIzraz unarniIzraz = getChild(0);

                addNodeCheckToTasks(unarniIzraz);
                addProcedureToTasks(() -> {
                    type = unarniIzraz.type;
                    leftAssignableExpression = unarniIzraz.leftAssignableExpression;
                });
                break;
            }
            case 4: {
                ImeTipa imeTipa = getChild(0);
                CastIzraz castIzraz = getChild(2);

                addNodeCheckToTasks(imeTipa);
                addNodeCheckToTasks(castIzraz);
                //            addErrorCheckToTasks(() -> castIzraz.type.); //todo check

                addProcedureToTasks(() -> {
                    type = imeTipa.type;
                    leftAssignableExpression = false;
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
