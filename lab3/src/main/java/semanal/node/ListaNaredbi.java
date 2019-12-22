package semanal.node;

import java.util.ArrayList;

public class ListaNaredbi extends Node {
	public ListaNaredbi(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
