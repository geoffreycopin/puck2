package app;

import graph.Edge;
import graph.Node;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;

import java.util.*;
import java.util.stream.Collectors;

public class Puck2Main {
    public static void main(String args[]) {
        Program p = loadProgram(args[0]);
        ProgramReader r = new ProgramReader(p);
        Map<String, Node> nodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        r.readInto(nodes, edges);

        List<Node> sorted_nodes = nodes.values().stream()
                .sorted(Comparator.comparingInt(Node::getId))
                .collect(Collectors.toList());

        for (Node n: sorted_nodes) {
            System.out.println(n);
        }

        for (Edge e: edges) {
            System.out.println(e);
        }
    }

    private static Program loadProgram(String path) {
        Program p = new Program();

        try {
            p.addSourceFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
}
