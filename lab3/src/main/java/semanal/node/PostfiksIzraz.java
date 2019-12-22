package semanal.node;

import java.util.ArrayList;

public class PostfiksIzraz extends Node {
	public PostfiksIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
