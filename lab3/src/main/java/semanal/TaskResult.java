package semanal;

import semanal.node.Node;

public class TaskResult {
    private Node nextNode;
    private boolean success;
    private String errorMessage;

    public TaskResult(Node nextNode, boolean success, String errorMessage) {
        this.nextNode = nextNode;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static TaskResult success(Node nextNode) {
        return new TaskResult(nextNode, true, "");
    }

    public static TaskResult failure(String errorMessage) {
        return new TaskResult(null, false, errorMessage);
    }

    public static TaskResult failure(Node node) {
        return failure(node.createErrorMessageAtNode());
    }

    public Node getNextNode() {
        return nextNode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
