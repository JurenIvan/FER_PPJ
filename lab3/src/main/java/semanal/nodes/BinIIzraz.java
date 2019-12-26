package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_I_IZRAZ;

public class BinIIzraz extends Node {

    public BinIIzraz(Node parent) {
        super(parent, BIN_I_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 59. str <--o
        o---------------o

        <bin_i_izraz> ::=
			<jednakosni_izraz>
			| <bin_i_izraz> OP_BIN_I <jednakosni_izraz>

         */

        // TODO

    }
}
