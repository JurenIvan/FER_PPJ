package semanal;

import semanal.node.Node;
import semanal.node.TerminalNode;

import java.util.List;

public class SemantickiAnalizator {

    private Node rootNode;

    /**
     * Program main entry point.
     *
     * @param args irrelevant
     */
    public static void main(String[] args) {
        System.out.print(new SemantickiAnalizator().run());
    }

    /**
     * Method to start the semantic analyser and return generated output.
     *
     * @return the output of the semantic analysis
     */
    public String run() {
        List<String> lines = Inputter.outputListString();

        rootNode = parseTree(lines);

        TaskResult taskResult = rootNode.nextTask();
        while (taskResult.getNextNode() != null && taskResult.isSuccess()) {
            System.out.println(taskResult.getNextNode().nodeType.symbolName);
            taskResult = taskResult.getNextNode().nextTask();
        }

        if (!taskResult.isSuccess()) {
            return taskResult.getErrorMessage();
        }

        //checkMain(); // provjera ima li maina, ako je uspjesno do sada
        // // jos neka provjera posle svega, ne sicam se koja

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
                currentNode = currentNode.parent;
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
                Integer lineNumber = Integer.parseInt(parts[1]);
                String source = parts[2];

                TerminalNode terminalNode = new TerminalNode(currentNode, terminalType, lineNumber, source);
                currentNode.addChild(terminalNode);
            }
        }

        return rootNode;
    }
}