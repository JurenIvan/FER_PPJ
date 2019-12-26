package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IME_TIPA;

public class ImeTipa extends Node {

    public ImeTipa(Node parent) {
        super(parent, IME_TIPA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 56. str <--o
        o---------------o

        <ime_tipa> ::=
            <specifikator_tipa>
            | KR_CONST <specifikator_tipa>

         */

        // TODO

    }
}
