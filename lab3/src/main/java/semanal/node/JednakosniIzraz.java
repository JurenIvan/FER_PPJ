package semanal.node;

import java.util.ArrayList;

public class JednakosniIzraz extends Node {
	public JednakosniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
