package semanal.node;

import java.util.ArrayList;

public class BinXiliIzraz extends Node {
	public BinXiliIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
