package semanal.node;

import java.util.ArrayList;

public class Naredba extends Node {
	public Naredba(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
