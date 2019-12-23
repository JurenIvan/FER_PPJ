package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.CAST_IZRAZ;

public class CastIzraz extends Node {

    public CastIzraz(Node parent) {
        super(parent, CAST_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 55. str <--o
        o---------------o

        <cast_izraz> ::=
            <unarni_izraz>
            | L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>

         */

        // TODO

    }
}
