package semanal.node;

import java.util.ArrayList;

public class ListaInitDeklaratora extends Node {
	public ListaInitDeklaratora(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
