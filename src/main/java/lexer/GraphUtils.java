package lexer;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GraphUtils {
	
	public static void runGraph(EnkaGraph enkaGraph) throws IOException {
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new SingleGraph("Tutorial 1");
		graph.addAttribute("ui.stylesheet",
				"node {\r\n" + "	text-size: 21px; size: 45px, 45px;\r\n" + "	shape: box;\r\n"
						+ "	fill-color: green;\r\n" + "	stroke-mode: plain;\r\n" + "	stroke-color: yellow;\r\n"
						+ "}\r\n" + "\r\n" + "edge {text-size: 15px;}node#A {\r\n" + "	fill-color: blue;\r\n" + "}\r\n"
						+ "\r\n" + "node:clicked {\r\n" + "	fill-color: red;\r\n" + "}");
		graph.setStrict(true);

		addToGraph(enkaGraph.getStartState(), graph);

		graph.display();
		for (Node node : graph) {
			node.addAttribute("ui.label", node.getId());
		}
		for (Edge edge : graph.getEdgeSet()) {
			edge.addAttribute("ui.label", edge.getId());
		}
	}

	private static void addToGraph(EnkaState x, Graph graph) {
		graph.addNode(x.getName());
		for (Entry<Character, List<EnkaState>> t : x.getAllTransitions().entrySet()) {
			for (EnkaState e : t.getValue()) {
				if (graph.getNode(e.getName()) == null) {
					addToGraph(e, graph);
				}
				if (!x.getName().equals(e.getName())) {
					Edge between = graph.getNode(x.getName()).getEdgeBetween(e.getName());
					if (between != null) {
						graph.removeEdge(between);
						graph.addEdge(between.getId() + "," + t.getKey(), x.getName(), e.getName(), true);
					} else {
						graph.addEdge("[" + x.getName() + "," + e.getName() + "]" + t.getKey(), x.getName(),
								e.getName(), true);
					}
				} else {
//				System.out.println("[" + x.getName() + "," + e.getName() + "]" + t.getKey());
				}
			}
		}
	}
}
