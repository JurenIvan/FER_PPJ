package semanal.node;

import java.util.ArrayList;

public class DeklaracijaParametra extends Node {
	public DeklaracijaParametra(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
