package semanal.nodes;

import semanal.Node;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public Type nType;
    public Type type;

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    /*
    o---------------o
    o--> 69. str <--o
    o---------------o

     <init_deklarator> ::=
			<izravni_deklarator>
			| <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

     */

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        switch (getChildrenNumber()) {
            case 1: {
                IzravniDeklarator izravniDeklarator = getChild(0);

                // 1.
                addProcedureToTasks(() -> izravniDeklarator.nType = nType);
                addNodeCheckToTasks(izravniDeklarator);
                // 2.
                addErrorCheckToTasks(
                        () -> izravniDeklarator.type.getSubType() != SubType.NUMBER || izravniDeklarator.type.getNumber().isNotConst());
                addErrorCheckToTasks(
                        () -> izravniDeklarator.type.getSubType() != SubType.ARRAY || izravniDeklarator.type.getArray().getNumberType()
                                .isNotConst());

                break;
            }
            case 3: {
                IzravniDeklarator izravniDeklarator = getChild(0);
                Inicijalizator inicijalizator = getChild(2);

                // 1.
                addProcedureToTasks(() -> izravniDeklarator.nType = nType);
                addNodeCheckToTasks(izravniDeklarator);
                // 2.
                addNodeCheckToTasks(inicijalizator);
                // 3.
                addErrorCheckToTasks(() -> {
                    if (izravniDeklarator.type.getSubType() == SubType.NUMBER) {
                        if (inicijalizator.type == null) {
                            return false;
                        }
                        return inicijalizator.type.implicitConvertInto(izravniDeklarator.type.getNumber().toNonConst());
                    } else if (izravniDeklarator.type.getSubType() == SubType.ARRAY) {
                        if (inicijalizator.types == null || inicijalizator.types.isEmpty())
                            return false;

                        if (izravniDeklarator.type.getArray().getNumberOfElements() < inicijalizator.numberOfElements)
                            return false;

                        if (inicijalizator.type != null && inicijalizator.type.getArray().getNumberType().isNotConst())
                            return false;

                        NumberType arrayNumberType = izravniDeklarator.type.getArray().getNumberType().toNonConst();
                        for (Type type : inicijalizator.types) {
                            if (!type.implicitConvertInto(arrayNumberType))
                                return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
