package semanal.node;

import java.util.ArrayList;

public class ListaIzrazaPridruzivanja extends Node {
	public ListaIzrazaPridruzivanja(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
