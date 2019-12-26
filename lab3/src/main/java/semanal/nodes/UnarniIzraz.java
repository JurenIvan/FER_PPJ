package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_IZRAZ;

public class UnarniIzraz extends Node {

    public UnarniIzraz(Node parent) {
        super(parent, UNARNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 54. str <--o
        o---------------o

        <unarni_izraz> ::=
            <postfiks_izraz>
            | OP_INC <unarni_izraz>
            | OP_DEC <unarni_izraz>
            | <unarni_operator> <cast_izraz>

         */

        // TODO

    }
}
