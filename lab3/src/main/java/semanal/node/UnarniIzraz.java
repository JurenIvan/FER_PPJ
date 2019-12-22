package semanal.node;

import java.util.ArrayList;

public class UnarniIzraz extends Node {
	public UnarniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
