package semanal.node;

import java.util.ArrayList;

public class NaredbaSkoka extends Node {
	public NaredbaSkoka(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
