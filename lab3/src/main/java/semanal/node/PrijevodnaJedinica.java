package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.PRIJEVODNA_JEDINICA;

public class PrijevodnaJedinica extends Node {

    public PrijevodnaJedinica(Node parent) {
        super(parent, PRIJEVODNA_JEDINICA);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
