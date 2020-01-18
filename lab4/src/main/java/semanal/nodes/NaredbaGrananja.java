package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.types.NumberType;

import java.util.ArrayList;

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
                String label = friscCodeAppender.generateLabel();
                tasks.add(() -> {
                    friscCodeAppender.appendCommand("POP R0");
                    friscCodeAppender.appendCommand("CMP R0, 0");
                    friscCodeAppender.appendCommand("JP_EQ " + label);
                    return TaskResult.success(this);
                });

                addNodeCheckToTasks(getChild(4));

                tasks.add(() -> {
                    friscCodeAppender.appendLabel(label);
                    return TaskResult.success(this);
                });

                break;
            }
            case 7: {
                Izraz izraz = getChild(2);

                addNodeCheckToTasks(izraz);
                addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(NumberType.INT));

                String label1 = friscCodeAppender.generateLabel();
                String label2 = friscCodeAppender.generateLabel();
                tasks.add(() -> {
                    friscCodeAppender.appendCommand("POP R0");
                    friscCodeAppender.appendCommand("CMP R0, 0");
                    friscCodeAppender.appendCommand("JP_EQ " + label1);
                    return TaskResult.success(this);
                });


                addNodeCheckToTasks(getChild(4));
                tasks.add(() -> {
                    friscCodeAppender.appendCommand("JP " + label2);
                    friscCodeAppender.appendLabel(label1);
                    return TaskResult.success(this);
                });
                addNodeCheckToTasks(getChild(6));
                tasks.add(() -> {
                    friscCodeAppender.appendLabel(label2);
                    return TaskResult.success(this);
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
