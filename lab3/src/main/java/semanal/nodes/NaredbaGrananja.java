package semanal.nodes;

import semanal.Node;
import semanal.types.NumberType;

import java.util.ArrayList;

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

                addNodeCheckToTasks(getChild(4));
                break;
            }
            case 7: {
                Izraz izraz = getChild(2);

                addNodeCheckToTasks(izraz);
                addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(NumberType.INT));

                addNodeCheckToTasks(getChild(4));
                addNodeCheckToTasks(getChild(6));
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
