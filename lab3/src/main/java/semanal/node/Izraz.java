package semanal.node;

import java.util.ArrayList;

public class Izraz extends Node {
	public Izraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
