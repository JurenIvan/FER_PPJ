package semanal.node;

import semanal.NodeType;
import semanal.TerminalType;

public class TerminalNode extends Node {

    private final TerminalType terminalType;
    private final int lineNumber;
    private final String sourceCode;

    public TerminalNode(Node parent, TerminalType terminalType, int lineNumber, String sourceCode) {
        super(parent, NodeType.TERMINAL);
        this.terminalType = terminalType;
        this.lineNumber = lineNumber;
        this.sourceCode = sourceCode;
    }

    @Override void initializeTasks() {
        throw new IllegalStateException("Terminal node is not permitted to initialize tasks.");
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
