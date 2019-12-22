package semanal.node;

import java.util.ArrayList;

public class CastIzraz extends Node {
	public CastIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
