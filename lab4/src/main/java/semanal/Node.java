package semanal;

import semanal.nodes.TerminalNode;
import semanal.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static semanal.WhereTo.INIT;
import static semanal.WhereTo.MAIN;

public abstract class Node {
    protected List<Supplier<TaskResult>> tasks;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private NodeType nodeType;
    private int currentTaskNumber = 0;
    private MemoryScope<Variable> variableMemory;
    private boolean alreadyCreatedLocalMemoryScope = false;
    protected FriscCodeAppender frisc;

    public Node(Node parent, NodeType nodeType) {
        this.parent = parent;
        this.nodeType = nodeType;
        frisc = FriscCodeAppender.getFriscCodeAppender();
    }

    public void createLocalVariableMemory() {
        if (alreadyCreatedLocalMemoryScope)
            throw new IllegalStateException("The same node cannot have two local memory scopes!");
        variableMemory = new MemoryScope<>(variableMemory);
    }

    public MemoryScope<Variable> getVariableMemory() {
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
            initialize();
        }

        if (tasks.isEmpty() || currentTaskNumber == tasks.size()) {
            return TaskResult.success(parent);
        }

        //        try {
        return tasks.get(currentTaskNumber++).get();
        //        } catch (Exception e) {
        //            throw new IllegalStateException(e);  // TODO remove debug
        //            // return TaskResult.failure(this);
        //        }
    }

    private void initialize() {
        if (parent != null) {
            variableMemory = parent.variableMemory;
        } else {
            variableMemory = new MemoryScope<>(null);
        }

        initializeTasks();
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

    public List<Node> getChildren() {
        return children;
    }

    public WhereTo whereTo() {
        return getVariableMemory().isGlobal() ? INIT : MAIN;
    }
}
