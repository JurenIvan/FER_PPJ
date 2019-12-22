package semanal.node;

import semanal.NodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Node {
    public Node parent;
    public List<Node> children = new ArrayList<>();

    public boolean addChild(Node child) {
        return children.add(child);
    }

    public NodeType nodeType;

    public int currentTask = 0;
    public List<Supplier<Node>> tasks;

    abstract void initializeTasks();

    public Node(Node parent, NodeType nodeType) {
        this.parent = parent;
        this.nodeType = nodeType;
    }
}
