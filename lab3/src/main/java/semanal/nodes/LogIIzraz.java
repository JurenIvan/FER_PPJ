package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LOG_I_IZRAZ;

public class LogIIzraz extends Node {

    public LogIIzraz(Node parent) {
        super(parent, LOG_I_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <log_i_izraz> ::=
			<bin_ili_izraz>
			| <log_i_izraz> OP_I <bin_ili_izraz>

         */

        // TODO

    }
}
