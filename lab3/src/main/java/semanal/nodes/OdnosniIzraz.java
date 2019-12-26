package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.ODNOSNI_IZRAZ;

public class OdnosniIzraz extends Node {

    public OdnosniIzraz(Node parent) {
        super(parent, ODNOSNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 58. str <--o
        o---------------o

        <odnosni_izraz> ::=
			<aditivni_izraz>
			| <odnosni_izraz> OP_LT <aditivni_izraz>
			| <odnosni_izraz> OP_GT <aditivni_izraz>
			| <odnosni_izraz> OP_LTE <aditivni_izraz>
			| <odnosni_izraz> OP_GTE <aditivni_izraz>

         */

        // TODO

    }
}
