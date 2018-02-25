package graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class XMLExporter {
    Map<String, Node> nodes;
    List<Edge> edges;

    public XMLExporter() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void writeTo(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(generateXml());
        writer.close();
    }

    public void add(Map<String, Node> nodes, List<Edge> edges) {
        this.nodes.putAll(nodes);
        this.edges.addAll(edges);
    }

    public String generateXml() {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\"?>\n");
        builder.append("<DependencyGraphe>\n");
        builder.append("\t<nodes>\n");

        for (Node n: nodes.values()) {
            builder.append(nodeToString(n));
        }

        builder.append("\t</nodes>\n");
        builder.append("\t<edges>\n");

        int currentEdgeIndex = 0;
        for (Edge e: edges) {
            builder.append(edgeToString(e, currentEdgeIndex++));
        }

        builder.append("\t</edges>\n");
        builder.append("</DependencyGraphe>\n");

        return builder.toString();
    }

    private String nodeToString(Node node) {
        String formatString = "\t\t<node type=\"%s\" id=\"%d\" name=\"%s\"/>\n";
        String type = node.getType().toString().toLowerCase();
        Integer id = node.getId();
        String name = extractNodeName(node.getFullName());

        return String.format(formatString, type, id, name);
    }

    private String extractNodeName(String nodeName) {
        String beforeLeftParenthesis = (nodeName.split("\\("))[0];
        String[] nameParts = beforeLeftParenthesis.split("\\.");

        return nameParts[nameParts.length - 1];
    }

    private String edgeToString(Edge edge, int id) {
        String formatString = "\t\t<edge type=\"%s\" src=\"%d\" dest=\"%s\" id=\"%d\"/>\n";
        String type = edge.getType().toString().toLowerCase();
        Integer src = nodes.get(edge.getSourceName()).getId();
        String tname = edge.getTargetName();
        Node tnode = nodes.get(tname);

        if (tnode != null) {
            System.out.println(edge);
            Integer dest = tnode.getId();
            return String.format(formatString, type, src, dest, id);
        }

        return "";
    }
}
