package semanal.node;

import java.util.ArrayList;

public class NaredbaGrananja extends Node {
	public NaredbaGrananja(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
