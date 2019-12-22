package semanal;

import semanal.node.Node;

import java.util.List;

public class SemantickiAnalizator {

    private Node rootNode;

    public static void main(String[] args) {
        System.out.print(new SemantickiAnalizator().run());
    }

	public static Node nextTask(Node node) {
		if (node.tasks == null || node.tasks.isEmpty()) {
			return null;
		}

		if (node.currentTask == node.tasks.size()) {
			return node.parent;
		}

		node = node.tasks.get(node.currentTask).get();
		node.currentTask++;
		return node;
	}

	public static Node pogreska(Node node) {
		System.out.println("Pogreska na produkciji kod ovog cvora: " + node);
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
			int currentDepth = line.length() - ltrim.length();

            int deltaDepth = currentDepth - lastDepth;
            lastDepth = currentDepth;

            if(deltaDepth>1)
                throw new IllegalStateException("Depth can only increase by one step, not " + deltaDepth);

            while(deltaDepth < 0){
                currentNode = currentNode.parent;
                deltaDepth++;
            }

            if (deltaDepth == 1 || deltaDepth == 0) {
                if (ltrim.startsWith("<")) {
                    Node newNode = NodeFactory.create(ltrim, currentNode);
                    currentNode.addChild(newNode);
                    currentNode = newNode;
                } else {
                    System.out.println("terminal --> " + ltrim); // TODO terminal
                }
            }
        }
        currentNode = rootNode;

		while (currentNode != null) {
			currentNode = nextTask(currentNode);
		}
		// provjera ima li maina, ako je uspjesno do sada
		// jos neka provjera posle svega, ne sicam se koja

        return sb.toString();
    }
}