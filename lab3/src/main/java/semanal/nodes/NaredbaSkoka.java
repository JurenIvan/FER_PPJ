package semanal.nodes;

import semanal.Node;
import semanal.TerminalType;
import semanal.types.NumberType;

import java.util.ArrayList;

import static semanal.NodeType.NAREDBA_SKOKA;

public class NaredbaSkoka extends Node {

    public NaredbaSkoka(Node parent) {
        super(parent, NAREDBA_SKOKA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 64. str <--o
        o---------------o

		<naredba_skoka> ::=
			KR_CONTINUE TOCKAZAREZ
			| KR_BREAK TOCKAZAREZ
			| KR_RETURN TOCKAZAREZ
			| KR_RETURN <izraz> TOCKAZAREZ

         */

        switch (getChildrenNumber()) {
            case 2: {
                TerminalNode firstChild = getChild(0);
                if (firstChild.getTerminalType() == TerminalType.KR_RETURN) {
                    // TODO pov je VOID
                } else {
                    // TODO u petlji smo ili bloku u petlji
                }
                break;
            }
            case 3: {
                Izraz izraz = getChild(1);

                addNodeCheckToTasks(izraz);
                // addErrorCheckToTasks(() -> izraz.type.implicitConvertInto(TODO pov));

                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
