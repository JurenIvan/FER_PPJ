package semanal.node;

import java.util.ArrayList;

public class OdnosniIzraz extends Node {
	public OdnosniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
