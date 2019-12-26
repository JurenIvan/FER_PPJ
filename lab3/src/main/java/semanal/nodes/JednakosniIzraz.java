package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.JEDNAKOSNI_IZRAZ;

public class JednakosniIzraz extends Node {

    public JednakosniIzraz(Node parent) {
        super(parent, JEDNAKOSNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 58. str <--o
        o---------------o

        <jednakosni_izraz> ::=
			<odnosni_izraz>
			| <jednakosni_izraz> OP_EQ <odnosni_izraz>
			| <jednakosni_izraz> OP_NEQ <odnosni_izraz>

         */

        // TODO

    }
}
