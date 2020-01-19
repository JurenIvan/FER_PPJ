package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.types.NumberType;

import java.util.ArrayList;

import static semanal.FriscCodeAppender.generateLabel;
import static semanal.NodeType.NAREDBA_GRANANJA;

public class NaredbaGrananja extends Node {

    public NaredbaGrananja(Node parent) {
        super(parent, NAREDBA_GRANANJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 63. str <--o
        o---------------o

		<naredba_grananja> ::=
			KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
			| KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>

         */

        switch (getChildrenNumber()) {
            case 5: {
                Izraz izraz = getChild(2);

                addNodeCheckToTasks(izraz);
                addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(NumberType.INT));
                String label = generateLabel();
                tasks.add(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append("CMP R0, 0", whereTo());
                    frisc.append("JP_EQ " + label, whereTo());
                    return TaskResult.success(this);
                });

                addNodeCheckToTasks(getChild(4));

                tasks.add(() -> {
                    frisc.appendLabelToMain(label);
                    return TaskResult.success(this);
                });

                break;
            }
            case 7: {
                Izraz izraz = getChild(2);

                addNodeCheckToTasks(izraz);
                addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(NumberType.INT));

                String label1 = generateLabel();
                String label2 = generateLabel();
                tasks.add(() -> {
                    frisc.append("POP R0", whereTo());
                    frisc.append("CMP R0, 0", whereTo());
                    frisc.append("JP_EQ " + label2, whereTo());
                    return TaskResult.success(this);
                });

                addNodeCheckToTasks(getChild(4));
                addProcedureToTasks(() -> {
                    frisc.append("JP " + label1, whereTo());
                    frisc.appendLabelToMain(label2);
                });

                addNodeCheckToTasks(getChild(6));
                addProcedureToTasks(() -> frisc.appendLabelToMain(label1));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
