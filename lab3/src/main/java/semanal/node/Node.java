package semanal.node;

import semanal.NodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Node {
    public Node parent;
    public List<Node> children = new ArrayList<>();

    public NodeType nodeType;

    public int currentTaskNumber = 0;
    public List<Supplier<Node>> tasks;

    public Node(Node parent, NodeType nodeType) {
        this.parent = parent;
        this.nodeType = nodeType;
    }

    public boolean addChild(Node child) {
        return children.add(child);
    }

    abstract void initializeTasks();

    public Node nextTask() {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }

        if (currentTaskNumber == tasks.size()) {
            return parent;
        }

        return tasks.get(currentTaskNumber++).get();
    }

}
