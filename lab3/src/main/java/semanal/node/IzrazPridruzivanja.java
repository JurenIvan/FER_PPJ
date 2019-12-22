package semanal.node;

import java.util.ArrayList;

public class IzrazPridruzivanja extends Node {
	public IzrazPridruzivanja(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
