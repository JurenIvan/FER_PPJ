package semanal.node;

import java.util.ArrayList;

public class ImeTipa extends Node {
	public ImeTipa(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
