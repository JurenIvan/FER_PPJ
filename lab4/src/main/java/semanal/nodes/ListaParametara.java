package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.List;

import static semanal.NodeType.LISTA_PARAMETARA;

public class ListaParametara extends Node {

    public List<Type> types = new ArrayList<>();
    public List<String> names = new ArrayList<>();

    public ListaParametara(Node parent) {
        super(parent, LISTA_PARAMETARA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();


        /*
        o---------------o
        o--> 67. str <--o
        o---------------o

        <lista_parametara> ::=
            <deklaracija_parametra>
            | <lista_parametara> ZAREZ <deklaracija_parametra>

         */

        switch (getChildrenNumber()) {
            case 1: {
                DeklaracijaParametra deklaracijaParametra = getChild(0);

                addNodeCheckToTasks(deklaracijaParametra);

                addProcedureToTasks(() -> {
                    types.add(deklaracijaParametra.type);
                    names.add(deklaracijaParametra.name);
                });
                break;
            }
            case 3: {
                ListaParametara listaParametara = getChild(0);
                DeklaracijaParametra deklaracijaParametra = getChild(2);

                addNodeCheckToTasks(listaParametara);
                addNodeCheckToTasks(deklaracijaParametra);
                addErrorCheckToTasks(() -> !listaParametara.names.contains(deklaracijaParametra.name));

                addProcedureToTasks(() -> {
                    types.addAll(listaParametara.types);
                    types.add(deklaracijaParametra.type);
                    names.addAll(listaParametara.names);
                    names.add(deklaracijaParametra.name);
                });
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
