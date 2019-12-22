package semanal.node;

import java.util.ArrayList;

public class IzravniDeklarator extends Node {
	public IzravniDeklarator(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
