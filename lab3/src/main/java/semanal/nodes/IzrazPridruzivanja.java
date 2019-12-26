package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ_PRIDRUZIVANJA;

public class IzrazPridruzivanja extends Node {

    public IzrazPridruzivanja(Node parent) {
        super(parent, IZRAZ_PRIDRUZIVANJA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 61. str <--o
        o---------------o

        <izraz_pridruzivanja> ::=
			<log_ili_izraz>
			| <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>

         */

        // TODO

    }
}
