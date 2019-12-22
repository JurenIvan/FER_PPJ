package semanal.node;

import java.util.ArrayList;

public class AditivniIzraz extends Node{
	public AditivniIzraz(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}



