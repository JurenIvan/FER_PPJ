package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.SPECIFIKATOR_TIPA;

public class SpecifikatorTipa extends Node {

    public SpecifikatorTipa(Node parent) {
        super(parent, SPECIFIKATOR_TIPA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 56. str <--o
        o---------------o

        <specifikator_tipa> ::=
            KR_VOID
            | KR_CHAR
            | KR_INT

         */

        // TODO

    }
}
