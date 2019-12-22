package semanal.node;

import java.util.ArrayList;

public class MultiplikativniIzraz extends Node {
	public MultiplikativniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
