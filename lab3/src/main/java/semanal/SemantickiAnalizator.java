package semanal;

import semanal.node.Node;
import semanal.node.TerminalNode;

import java.util.List;

public class SemantickiAnalizator {

    private Node rootNode;

    public static void main(String[] args) {
        System.out.print(new SemantickiAnalizator().run());
    }

    public static Node error(Node node) {
        StringBuilder sb = new StringBuilder();

        sb.append(node.nodeType.symbolName);
        sb.append(" ::=");
        for (Node child : node.children) {
            sb.append(" ");
            if (node.nodeType == NodeType.TERMINAL) {
                TerminalNode terminalNode = (TerminalNode) node;
                sb.append(terminalNode.getTerminalType());
                sb.append("(");
                sb.append(terminalNode.getLineNumber());
                sb.append(",");
                sb.append(terminalNode.getSourceCode());
                sb.append(")");
            } else {
                sb.append(node.nodeType.symbolName);
            }
        }

        //        return sb.toString(); // TODO return somehow this error value
        System.out.println(sb.toString()); // TODO remove, deubug only
        return null;
    }

    public String run() {
        StringBuilder sb = new StringBuilder();
        List<String> lines = Inputter.outputListString();

        rootNode = NodeFactory.create(lines.get(0), null);

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
        currentNode = rootNode;

        while (currentNode != null) {
            currentNode = currentNode.nextTask();
        }
        // provjera ima li maina, ako je uspjesno do sada
        // jos neka provjera posle svega, ne sicam se koja

        return sb.toString();
    }
}