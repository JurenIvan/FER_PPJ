package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.DEKLARACIJA;

public class Deklaracija extends Node {

    public Deklaracija(Node parent) {
        super(parent, DEKLARACIJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 68. str <--o
        o---------------o


        <deklaracija> ::=
			<ime_tipa> <lista_init_deklaratora> TOCKAZAREZ

         */

        if (hasNChildren(3)) {
            ImeTipa imeTipa = getChild(0);
            ListaInitDeklaratora listaInitDeklaratora = getChild(1);

            addNodeCheckToTasks(imeTipa);
            addProcedureToTasks(() -> listaInitDeklaratora.nType = imeTipa.type);
            addNodeCheckToTasks(listaInitDeklaratora);
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
