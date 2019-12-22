package semanal.node;

import java.util.ArrayList;

public class PrimarniIzraz extends Node {
	public PrimarniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
