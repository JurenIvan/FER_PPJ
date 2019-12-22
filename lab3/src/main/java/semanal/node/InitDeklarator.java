package semanal.node;

import java.util.ArrayList;

public class InitDeklarator extends Node {
	public InitDeklarator(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
