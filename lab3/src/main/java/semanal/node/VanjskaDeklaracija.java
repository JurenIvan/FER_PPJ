package semanal.node;

import java.util.ArrayList;

public class VanjskaDeklaracija extends Node {
	public VanjskaDeklaracija(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
