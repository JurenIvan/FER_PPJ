package semanal.node;

import java.util.ArrayList;

public class Deklaracija extends Node {
	public Deklaracija(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
