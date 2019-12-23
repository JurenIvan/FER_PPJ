package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.LOG_ILI_IZRAZ;

public class LogIliIzraz extends Node {

    public LogIliIzraz(Node parent) {
        super(parent, LOG_ILI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 60. str <--o
        o---------------o

        <log_ili_izraz> ::=
			<log_i_izraz>
			| <log_ili_izraz> OP_ILI <log_i_izraz>

         */

        // TODO

    }
}
