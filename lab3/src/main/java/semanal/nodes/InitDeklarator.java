package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.INIT_DEKLARATOR;

public class InitDeklarator extends Node {

    public Type type;

    public InitDeklarator(Node parent) {
        super(parent, INIT_DEKLARATOR);
    }

    /*
      69. str

     <init_deklarator> ::=
			<izravni_deklarator>
			| <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

     */

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();

        if (hasNChildren(2)) {
            ImeTipa imeTipa = getChild(0);
            ListaInitDeklaratora listaInitDeklaratora = getChild(1);

            this.type = imeTipa.type;

            listaInitDeklaratora.type = this.type;

            addNodeCheckToTasks(imeTipa);
            addNodeCheckToTasks(listaInitDeklaratora);
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
