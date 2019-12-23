package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.ADITIVNI_IZRAZ;

public class AditivniIzraz extends Node {

    public AditivniIzraz(Node parent) {
        super(parent, ADITIVNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 57. str <--o
        o---------------o

        <aditivni_izraz> ::=
			<multiplikativni_izraz>
			| <aditivni_izraz> PLUS <multiplikativni_izraz>
			| <aditivni_izraz> MINUS <multiplikativni_izraz>

         */

        // TODO

    }
}



