package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.BIN_XILI_IZRAZ;

public class BinXiliIzraz extends Node {

    public BinXiliIzraz(Node parent) {
        super(parent, BIN_XILI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 59. str <--o
        o---------------o

        <bin_xili_izraz> ::=
			<bin_i_izraz>
			| <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>

         */

        // TODO

    }
}
