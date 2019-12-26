package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_OPERATOR;

public class UnarniOperator extends Node {

    public UnarniOperator(Node parent) {
        super(parent, UNARNI_OPERATOR);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 55. str <--o
        o---------------o

        <unarni_operator> ::=
            PLUS
            | MINUS
            | OP_TILDA
            | OP_NEG

         */

        // TODO
    }
}
