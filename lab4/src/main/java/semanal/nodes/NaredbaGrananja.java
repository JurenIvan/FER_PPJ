package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.WhereTo;
import semanal.types.NumberType;

import java.util.ArrayList;

import static semanal.FriscCodeAppender.*;
import static semanal.NodeType.NAREDBA_GRANANJA;

public class NaredbaGrananja extends Node {

    public NaredbaGrananja(Node parent) {
        super(parent, NAREDBA_GRANANJA);
    }

    @Override
    protected void initializeTasks() {
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
                    friscCodeAppender.append("POP R0", WhereTo.MAIN);
                    friscCodeAppender.append("CMP R0, 0", WhereTo.MAIN);
                    friscCodeAppender.append("JP_EQ " + label, WhereTo.MAIN);
                    return TaskResult.success(this);
                });

                addNodeCheckToTasks(getChild(4));

                tasks.add(() -> {
                    friscCodeAppender.appendLabelToMain(label);
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
                    friscCodeAppender.append("POP R0", WhereTo.MAIN);
                    friscCodeAppender.append("CMP R0, 0", WhereTo.MAIN);
                    friscCodeAppender.append("JP_EQ " + label1, WhereTo.MAIN);
                    return TaskResult.success(this);
                });


                addNodeCheckToTasks(getChild(4));
                tasks.add(() -> {
                    friscCodeAppender.append("JP " + label2, WhereTo.MAIN);
                    friscCodeAppender.appendLabelToMain(label1);
                    return TaskResult.success(this);
                });
                addNodeCheckToTasks(getChild(6));
                tasks.add(() -> {
                    friscCodeAppender.appendLabelToMain(label2);
                    return TaskResult.success(this);
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
