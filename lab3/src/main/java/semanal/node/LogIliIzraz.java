package semanal.node;

import java.util.ArrayList;

public class LogIliIzraz extends Node {
	public LogIliIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
