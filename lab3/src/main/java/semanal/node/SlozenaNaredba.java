package semanal.node;

import java.util.ArrayList;

public class SlozenaNaredba extends Node {
	public SlozenaNaredba(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
