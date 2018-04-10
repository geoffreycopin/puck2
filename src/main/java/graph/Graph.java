package graph;

import org.extendj.ast.Program;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    Map<String, Node> nodes;
    Map<String, Set<Edge>> fromIndex = new HashMap<>();
    Map<String, Set<Edge>> toIndex = new HashMap<>();
    Map<String, String> newNodeName = new HashMap<>();
    Map<String, List<String>> previousNodeNames = new HashMap<>();
    Set<Edge> edges;
    Program program;

    public Graph(Map<String, Node> nodes, Set<Edge> edges, Program program) {
        this.nodes = nodes;
        this.edges = edges;
        this.program = program;
        initIndexes();
    }

    public void initIndexes() {
        for (Edge e: edges) {
            Set<Edge> fromList = fromIndex.getOrDefault(e.getSourceName(), new HashSet<>());
            fromList.add(e);
            fromIndex.put(e.getSourceName(), fromList);
          

            Set<Edge> toList = toIndex.getOrDefault(e.getTargetName(), new HashSet<>());
            toList.add(e);
            toIndex.put(e.getTargetName(), toList);
        }
    }

    public Program getProgram() {
        return program;
    }

    public Node getNode(String id) {
        return nodes.get(getNodeName(id));
    }

    public List<Node> queryFrom(String id, Edge.Type type) {
        id = getNodeName(id);
        return queryIndex(fromIndex, id, type).stream()
                .map((e) -> nodes.get(e.getTargetName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Node> queryTo(String id, Edge.Type type) {
        id = getNodeName(id);
        return queryIndex(toIndex, id, type).stream()
                .map((e) -> nodes.get(e.getSourceName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Edge> queryIndex(Map<String, Set<Edge>> index, String id, Edge.Type type) {
        if (index.get(id) == null) {
            return new ArrayList<>();
        }
        return index.get(id).stream()
                .filter((e) -> e.getType() == type)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getNodeName(String id) {
        return newNodeName.getOrDefault(id, id);
    }

    public void renameNode(String oldName, String newName) {
        if (nodes.get(oldName) == null || newName.isEmpty()) {
            return;
        }

        Node n = nodes.remove(oldName);
        nodes.put(newName, n);

        updatePreviousNames(oldName, newName);
    }

    private void updatePreviousNames(String oldName, String newName) {
        List<String> previousNames = previousNodeNames.getOrDefault(oldName, new ArrayList<>());
        for (String name: previousNodeNames.getOrDefault(oldName, new ArrayList<>())) {
            newNodeName.put(name, newName);
        }
        previousNames.add(oldName);
        previousNodeNames.remove(oldName);
        previousNodeNames.put(newName, previousNames);
    }
}
