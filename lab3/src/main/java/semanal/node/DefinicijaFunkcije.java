package semanal.node;

import java.util.ArrayList;

public class DefinicijaFunkcije extends Node {
	public DefinicijaFunkcije(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
