package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.PRIJEVODNA_JEDINICA;

public class PrijevodnaJedinica extends Node {

    public PrijevodnaJedinica(Node parent) {
        super(parent, PRIJEVODNA_JEDINICA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
