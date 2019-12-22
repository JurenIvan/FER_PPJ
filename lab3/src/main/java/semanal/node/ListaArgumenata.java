package semanal.node;

import java.util.ArrayList;

public class ListaArgumenata extends Node {
	public ListaArgumenata(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
