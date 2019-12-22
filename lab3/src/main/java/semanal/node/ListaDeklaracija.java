package semanal.node;

import java.util.ArrayList;

public class ListaDeklaracija extends Node {
	public ListaDeklaracija(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
