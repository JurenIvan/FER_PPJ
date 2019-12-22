package semanal.node;

import java.util.ArrayList;

public class Inicijalizator extends Node {
	public Inicijalizator(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
