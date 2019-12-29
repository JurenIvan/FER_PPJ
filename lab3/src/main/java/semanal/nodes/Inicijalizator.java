package semanal.nodes;

import semanal.Node;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static semanal.NodeType.INICIJALIZATOR;

public class Inicijalizator extends Node {

    public Type type;
    public List<Type> types;
    public Integer numberOfElements;

    public Inicijalizator(Node parent) {
        super(parent, INICIJALIZATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 71. str <--o
        o---------------o

        <inicijalizator> ::=
			<izraz_pridruzivanja>
			| L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA

         */

        switch (getChildrenNumber()) {
            case 1: {
                IzrazPridruzivanja izrazPridruzivanja = getChild(0);

                addNodeCheckToTasks(izrazPridruzivanja);

                addErrorCheckToTasks(() -> {
                    // this was spicy TODO check
                    if (izrazPridruzivanja.type.getSubType() == SubType.ARRAY) {
                        // if (!izrazPridruzivanja.type.getArray().isInitializedByString())
                        //     return false;
                        numberOfElements = izrazPridruzivanja.type.getArray().getNumberOfElements();
                        types = new ArrayList<>(Collections.nCopies(numberOfElements, Type.createNumber(NumberType.CHAR)));
                        // types = new ArrayList<>(Collections.nCopies(numberOfElements, Type.createNumber(izrazPridruzivanja.type.getArray().getNumberType().toNonConst())));
                        type = izrazPridruzivanja.type;
                    } else {
                        type = izrazPridruzivanja.type;
                    }
                    return true;
                });
                break;
            }
            case 3: {
                ListaIzrazaPridruzivanja listaIzrazaPridruzivanja = getChild(1);

                addNodeCheckToTasks(listaIzrazaPridruzivanja);

                addProcedureToTasks(() -> {
                    numberOfElements = listaIzrazaPridruzivanja.numberOfElements;
                    types = listaIzrazaPridruzivanja.argumentTypes;
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
