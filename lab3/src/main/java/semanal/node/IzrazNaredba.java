package semanal.node;

import java.util.ArrayList;

public class IzrazNaredba extends Node {
	public IzrazNaredba(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
