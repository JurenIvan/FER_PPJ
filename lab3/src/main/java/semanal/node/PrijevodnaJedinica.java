package semanal.node;

import java.util.ArrayList;

public class PrijevodnaJedinica extends Node {
	public PrijevodnaJedinica(Node parent) {
		super(parent);
	}

	@Override void initializeTasks() {
		tasks = new ArrayList<>();
	}
}
