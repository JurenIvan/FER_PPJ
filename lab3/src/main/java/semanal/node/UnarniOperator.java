package semanal.node;

import java.util.ArrayList;

import static semanal.NodeType.UNARNI_OPERATOR;

public class UnarniOperator extends Node {

    public UnarniOperator(Node parent) {
        super(parent, UNARNI_OPERATOR);
    }

    @Override
    void initializeTasks() {
        tasks = new ArrayList<>();
    }
}
