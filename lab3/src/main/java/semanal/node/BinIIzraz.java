package semanal.node;

import java.util.ArrayList;

public class BinIIzraz extends Node {
	public BinIIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
