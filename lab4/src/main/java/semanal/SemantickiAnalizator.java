package semanal;

import semanal.nodes.TerminalNode;
import semanal.types.FunctionModel;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;
import semanal.variables.Variable;

import java.util.*;

public class SemantickiAnalizator {

    private static String MAIN_ERROR_MESSAGE = "main";
    private static String FUNCTION_ERROR_MESSAGE = "funkcija";

    /**
     * Program main entry point.
     *
     * @param args irrelevant
     */
    public static void main(String[] args) {
        System.out.println(new SemantickiAnalizator().run());
    }

    /**
     * Method to start the semantic analyser and return generated output.
     *
     * @return the output of the semantic analysis
     */
    public String run() {
        List<String> lines = Inputter.outputListString();

        Node rootNode = parseTree(lines);

        TaskResult taskResult = rootNode.nextTask();
        while (taskResult.getNextNode() != null && taskResult.isSuccess()) {
            // System.out.println("-->" + taskResult.getNextNode().getNodeType().symbolName); // TODO remove debug
            taskResult = taskResult.getNextNode().nextTask();
        }

        if (!taskResult.isSuccess()) {
            return taskResult.getErrorMessage();
        }

        List<Node> nodesToVisit = new ArrayList<>();
        nodesToVisit.add(rootNode);
        Set<MemoryScope<Variable>> memoryScopeSet = new HashSet<>();

        while (!nodesToVisit.isEmpty()) {
            Node node = nodesToVisit.remove(nodesToVisit.size() - 1);
            if (node.getNodeType() == NodeType.TERMINAL)
                continue;
            nodesToVisit.addAll(node.getChildren());
            if (node.getVariableMemory() != null) {
                memoryScopeSet.add(node.getVariableMemory());
            }
        }

        boolean mainOK = true;
        boolean functionOK = true;
        HashMap<String, FunctionModel> declared = new HashMap<>();
        HashMap<String, FunctionModel> defined = new HashMap<>();
        for (MemoryScope<Variable> scope : memoryScopeSet) {
            for (Variable var : scope.getMemory()) {
                if (var.getElementType().getSubType() == SubType.FUNCTION) {
                    FunctionModel old;
                    if (var.getElementType().getFunction().isDefined()) {
                        old = defined.put(var.getName(), var.getElementType().getFunction());
                    } else {
                        old = declared.put(var.getName(), var.getElementType().getFunction());
                    }
                    if (old != null && !old.equals(var.getElementType().getFunction())) {
                        functionOK = false;
                    }
                }
            }
        }

        if (!defined.containsKey("main") || !defined.get("main").acceptsVoid()
                || !defined.get("main").getReturnValueType().equals(Type.createNumber(NumberType.INT))) {
            mainOK = false;
        }

        for (Map.Entry<String, FunctionModel> declaredFunction : declared.entrySet()) {
            if (!defined.containsKey(declaredFunction.getKey()) || !defined.get(declaredFunction.getKey())
                    .equals(declaredFunction.getValue())) {
                functionOK = false;
            }
        }

        if (!mainOK)
            return MAIN_ERROR_MESSAGE;
        if (!functionOK)
            return FUNCTION_ERROR_MESSAGE;

        return "";
    }

    /**
     * Help method used to generate {@link Node} tree from syntax tree given by input lines.
     *
     * @param lines syntax tree lines
     * @return the root of the tree
     */
    private Node parseTree(List<String> lines) {
        Node rootNode = NodeFactory.create(lines.get(0), null);

        int lastDepth = 0;
        Node currentNode = rootNode;
        for (int i = 1, nlines = lines.size(); i < nlines; i++) {
            String line = lines.get(i);
            String ltrim = line.replaceAll("^\\s+", "");
            if (ltrim.isEmpty())
                continue;

            int currentDepth = line.length() - ltrim.length();

            int deltaDepth = currentDepth - lastDepth;
            lastDepth = currentDepth;

            if (deltaDepth > 1)
                throw new IllegalStateException("Depth can only increase by one step, not " + deltaDepth);

            while (deltaDepth < 0) {
                currentNode = currentNode.getParent();
                deltaDepth++;
            }

            if (ltrim.startsWith("<")) {
                Node newNode = NodeFactory.create(ltrim, currentNode);
                currentNode.addChild(newNode);
                currentNode = newNode;
            } else {
                String[] parts = ltrim.split(" ", 3);
                if (parts.length != 3) {
                    throw new IllegalArgumentException(
                            "Terminal leaf node has invalid format. The node should have three parts separated with spaces");
                }

                TerminalType terminalType = TerminalType.valueOf(parts[0]);
                int lineNumber = Integer.parseInt(parts[1]);
                String source = parts[2];

                TerminalNode terminalNode = new TerminalNode(currentNode, terminalType, lineNumber, source);
                currentNode.addChild(terminalNode);
            }
        }

        return rootNode;
    }
}