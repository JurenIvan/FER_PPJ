package semanal.node;

import java.util.ArrayList;

public class LogIIzraz extends Node {
	public LogIIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
