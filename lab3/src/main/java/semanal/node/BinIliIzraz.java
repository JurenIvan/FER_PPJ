package semanal.node;

import java.util.ArrayList;

public class BinIliIzraz extends Node {
	public BinIliIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
