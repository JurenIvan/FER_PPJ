package semanal.node;

import java.util.ArrayList;

public class UnarniOperator extends Node {
	public UnarniOperator(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
