package semanal;

import semanal.nodes.TerminalNode;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Node {
    protected List<Supplier<TaskResult>> tasks;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private NodeType nodeType;
    private int currentTaskNumber = 0;
    private MemoryScope<Type> variableMemory;
    private boolean alreadyCreatedLocalMemoryScope = false;

    public Node(Node parent, NodeType nodeType) {
        this.parent = parent;
        this.nodeType = nodeType;
        if (parent != null) {
            variableMemory = parent.variableMemory;
        } else {
            variableMemory = new MemoryScope<>(null);
        }
    }

    public void createLocalVariableMemory() {
        if (alreadyCreatedLocalMemoryScope)
            throw new IllegalStateException("The same node cannot have two local memory scopes!");
        variableMemory = new MemoryScope<>(variableMemory);
    }

    public MemoryScope<Type> getVariableMemory() {
        return variableMemory;
    }

    public Node getParent() {
        return parent;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public boolean addChild(Node child) {
        return children.add(child);
    }

    public int getChildrenNumber() {
        return children.size();
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

    protected abstract void initializeTasks();

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

    protected void addNodeCheckToTasks(Node node) {
        tasks.add(() -> TaskResult.success(node));
    }

    protected void addProcedureToTasks(Procedure p) {
        tasks.add(() -> {
            p.run();
            return TaskResult.success(this);
        });
    }

    protected void addErrorCheckToTasks(Supplier<Boolean> booleanSupplier) {
        tasks.add(() -> {
            if (!booleanSupplier.get())
                return TaskResult.failure(this);

            return TaskResult.success(this);
        });
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
            if (child.nodeType == NodeType.TERMINAL) {
                TerminalNode terminalNode = child.toTerminalNode();
                sb.append(terminalNode.getTerminalType());
                sb.append("(");
                sb.append(terminalNode.getLineNumber());
                sb.append(",");
                sb.append(terminalNode.getSourceCode());
                sb.append(")");
            } else {
                sb.append(child.nodeType.symbolName);
            }
        }

        return sb.toString();
    }
}
