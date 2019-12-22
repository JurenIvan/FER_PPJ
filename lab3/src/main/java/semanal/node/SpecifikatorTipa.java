package semanal.node;

import java.util.ArrayList;

public class SpecifikatorTipa extends Node {
	public SpecifikatorTipa(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
