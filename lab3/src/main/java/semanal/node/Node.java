package semanal.node;

import semanal.NodeType;
import semanal.TaskResult;
import semanal.TerminalType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Node {
    public Node parent;
    public List<Node> children = new ArrayList<>();

    public NodeType nodeType;

    public int currentTaskNumber = 0;
    public List<Supplier<TaskResult>> tasks;

    public Node(Node parent, NodeType nodeType) {
        this.parent = parent;
        this.nodeType = nodeType;
    }

    public boolean addChild(Node child) {
        return children.add(child);
    }

    public boolean hasNChildren(int n) {
        return children.size() == n;
    }

    public boolean isChildOfType(int i, NodeType nodeType) {
        return children.get(i).nodeType == nodeType;
    }

    public boolean isChildOfTerminalType(int i, TerminalType terminalType) {
        return children.get(i).isTerminalOfType(terminalType);
    }

    public <T extends Node> T getChild(int i) {
        return (T) children.get(i);
    }

    abstract void initializeTasks();

    public TaskResult nextTask() {
        if (tasks == null) {
            initializeTasks();

            // <! DUMMY -- adding dummy tasks, only to visit all tree nodes>
            for (var child : children) {
                if (child.nodeType == NodeType.TERMINAL)
                    continue;
                tasks.add(() -> {
                    return TaskResult.success(child);
                });
            }
            // <DUMMY !>

        }

        if (tasks.isEmpty() || currentTaskNumber == tasks.size()) {
            return TaskResult.success(parent);
        }

        return tasks.get(currentTaskNumber++).get();
    }

    public boolean isTerminalOfType(TerminalType terminalType) {
        if (nodeType == NodeType.TERMINAL && toTerminalNode().getTerminalType() == terminalType)
            return true;

        return false;
    }

    public TerminalNode toTerminalNode() {
        return (TerminalNode) this;
    }

    public String createErrorMessageAtNode() {
        StringBuilder sb = new StringBuilder();

        sb.append(nodeType.symbolName);
        sb.append(" ::=");
        for (Node child : children) {
            sb.append(" ");
            if (nodeType == NodeType.TERMINAL) {
                TerminalNode terminalNode = toTerminalNode();
                sb.append(terminalNode.getTerminalType());
                sb.append("(");
                sb.append(terminalNode.getLineNumber());
                sb.append(",");
                sb.append(terminalNode.getSourceCode());
                sb.append(")");
            } else {
                sb.append(nodeType.symbolName);
            }
        }

        return sb.toString();
    }

}
