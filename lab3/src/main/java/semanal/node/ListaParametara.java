package semanal.node;

import java.util.ArrayList;

public class ListaParametara extends Node {
	public ListaParametara(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
