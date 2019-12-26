package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_ILI_IZRAZ;

public class BinIliIzraz extends Node {

    public BinIliIzraz(Node parent) {
        super(parent, BIN_ILI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <bin_ili_izraz> ::=
			<bin_xili_izraz>
			| <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>

         */

        // TODO

    }
}
