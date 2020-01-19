package semanal.nodes;

import semanal.FriscCodeAppender;
import semanal.Node;
import semanal.types.NumberType;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_PETLJE;

public class NaredbaPetlje extends Node {

    public NaredbaPetlje(Node parent) {
        super(parent, NAREDBA_PETLJE);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();


        /*
        o---------------o
        o--> 64. str <--o
        o---------------o

		<naredba_petlje> ::=
			KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
			| KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>
			| KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>

         */

        switch (getChildrenNumber()) {
            case 5: {

                String labela1 = FriscCodeAppender.generateLabel();
                String labela2 = FriscCodeAppender.generateLabel();

                Izraz izraz = getChild(2);

                addProcedureToTasks(() -> {
                    frisc.appendLabelToMain(labela1);
                });
                addNodeCheckToTasks(izraz);
                addProcedureToTasks(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append("CMP R0, 0", whereTo());
                    frisc.append("JP_EQ " + labela2, whereTo());
                });
                addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(NumberType.INT));

                addNodeCheckToTasks(getChild(4));
                addProcedureToTasks(() -> {
                    frisc.append("JP " + labela1, whereTo());
                    frisc.appendLabelToMain(labela2);
                });
                break;
            }
            case 6: {
                IzrazNaredba izrazNaredba1 = getChild(2);
                IzrazNaredba izrazNaredba2 = getChild(3);

                addNodeCheckToTasks(izrazNaredba1);
                addNodeCheckToTasks(izrazNaredba2);

                addErrorCheckToTasks(() -> izrazNaredba2.type.implicitConvertInto(NumberType.INT));

                addNodeCheckToTasks(getChild(5));
                break;
            }
            case 7: {
                IzrazNaredba izrazNaredba1 = getChild(2);
                IzrazNaredba izrazNaredba2 = getChild(3);

                addNodeCheckToTasks(izrazNaredba1);
                addNodeCheckToTasks(izrazNaredba2);

                addErrorCheckToTasks(() -> izrazNaredba2.type.implicitConvertInto(NumberType.INT));

                addNodeCheckToTasks(getChild(4));
                addNodeCheckToTasks(getChild(6));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
