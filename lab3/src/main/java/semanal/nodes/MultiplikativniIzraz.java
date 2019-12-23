package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.MULTIPLIKATIVNI_IZRAZ;

public class MultiplikativniIzraz extends Node {

    public MultiplikativniIzraz(Node parent) {
        super(parent, MULTIPLIKATIVNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 57. str <--o
        o---------------o

        <multiplikativni_izraz> ::=
            <cast_izraz>
            | <multiplikativni_izraz> OP_PUTA <cast_izraz>
            | <multiplikativni_izraz> OP_DIJELI <cast_izraz>
            | <multiplikativni_izraz> OP_MOD <cast_izraz>

         */

        // TODO

    }
}
