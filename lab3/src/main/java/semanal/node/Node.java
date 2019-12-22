package semanal.node;

import semanal.SemantickiAnalizator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Node {
	public Node parent;
	public List<Node> children = new ArrayList<>();

	public boolean addChild(Node child) {
		return children.add(child);
	}

 //   public NodeType type; //todo is this necessery now ?

	public int currentTask = 0;
	public List<Supplier<Node>> tasks;

	abstract void initializeTasks();

	public Node(Node parent) {
		this.parent = parent;
	}
}
