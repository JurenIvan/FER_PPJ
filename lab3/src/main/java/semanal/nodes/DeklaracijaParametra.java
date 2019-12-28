package semanal.nodes;

import semanal.Node;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA_PARAMETRA;

public class DeklaracijaParametra extends Node {

    public Type type;
    public String name;

    public DeklaracijaParametra(Node parent) {
        super(parent, DEKLARACIJA_PARAMETRA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
        /*
        o---------------o
        o--> 67. str <--o
        o---------------o

		<deklaracija_parametra> ::=
			<ime_tipa> IDN
			| <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA

         */

        switch (getChildrenNumber()) {
            case 2: {
                ImeTipa imeTipa = getChild(0);
                TerminalNode idn = getChild(1);

                addNodeCheckToTasks(imeTipa);
                addErrorCheckToTasks(() -> !Type.VOID_TYPE.equals(imeTipa.type));

                addProcedureToTasks(() -> {
                    type = imeTipa.type;
                    name = idn.getSourceCode();
                });
                break;
            }
            case 4: {
                ImeTipa imeTipa = getChild(0);
                TerminalNode idn = getChild(1);

                addNodeCheckToTasks(imeTipa);
                addErrorCheckToTasks(() -> !Type.VOID_TYPE.equals(imeTipa.type));

                addErrorCheckToTasks(() -> imeTipa.type.getSubType() == SubType.NUMBER);
                addProcedureToTasks(() -> {
                    type = Type.createArray(imeTipa.type.getNumber(), 0); // TODO check
                    name = idn.getSourceCode();
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
