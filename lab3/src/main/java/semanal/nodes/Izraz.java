package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;

public class Izraz extends Node {

    public boolean lIzraz;
    public String tip;

    public Izraz(Node parent) {
        super(parent, IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 61. str <--o
        o---------------o

        <izraz> ::=
			<izraz_pridruzivanja>
			| <izraz> ZAREZ <izraz_pridruzivanja>

         */

        // TODO

    }
}