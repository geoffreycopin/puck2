package graph;

import org.extendj.ast.ASTNode;
import org.extendj.ast.ArrayAccess;
import org.extendj.ast.Program;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Map<Integer, Node> nodes = new HashMap<>();
    private Map<Integer, Set<Edge>> fromIndex = new HashMap<>();
    private Map<Integer, Set<Edge>> toIndex = new HashMap<>();
    private Map<String, Integer> nameIndex = new HashMap<>();
    private Set<Edge> edges = new HashSet<>();
    private Program program;
    private UniqueIdGenerator generator = new UniqueIdGenerator();
    private Map<Integer, List<ASTNode<ASTNode>>> references = new HashMap<>();

    public Graph(Map<Integer, Node> nodes, Set<Edge> edges, Program program) {
        this.nodes = nodes;
        this.edges = edges;
        this.program = program;
        initIndexes();
    }

    public Graph(Program program) {
        this.program = program;
    }

    private void initIndexes() {
        for (Edge e: edges) {
            addToIndex(fromIndex, e.getSource(), e);
            addToIndex(toIndex, e.getTarget(), e);
        }

        for (Node n: nodes.values()) {
            nameIndex.put(n.getFullName(), n.getId());
        }
    }

    private void addToIndex(Map<Integer, Set<Edge>> index, Integer id,Edge newEdge) {
        index.putIfAbsent(id, new HashSet<>());
        Set<Edge> s = index.get(id);
        s.add(newEdge);
    }

    public Node addNode(String name, Node.Type type, ASTNode<ASTNode> extendjNode) {
        Integer id = generator.idFor(name);
        Node newNode = new Node(id, name, type, extendjNode);
        this.nodes.put(id, newNode);
        this.nameIndex.put(name, id);
        return newNode;
    }

    public boolean addEdge(Integer source, Integer target, Edge.Type type, ASTNode<ASTNode> dependencyPoint) {
        Edge newEdge = new Edge(source, target, type, dependencyPoint);
        this.edges.add(newEdge);
        addToIndex(fromIndex, source, newEdge);
        addToIndex(toIndex, target, newEdge);

        return true;
    }

    public boolean addEdge(Integer source, Integer target, Edge.Type type) {
        return addEdge(source, target, type, null);
    }

    public boolean addEdge(String source, String target, Edge.Type type, ASTNode<ASTNode> dependencyPoint) {
        return addEdge(generator.idFor(source), generator.idFor(target), type, dependencyPoint);
    }

    public boolean addEdge(String source, String target, Edge.Type type) {
        return addEdge(generator.idFor(source), generator.idFor(target), type, null);
    }

    public void addReference(String fullName, ASTNode<ASTNode> ref) {
        Integer nodeId = generator.idFor(fullName);
        references.putIfAbsent(nodeId, new ArrayList<>());
        List<ASTNode<ASTNode>> r = references.get(generator.idFor(fullName));
        r.add(ref);
    }

    public List<ASTNode<ASTNode>> getReferences(Integer id) {
        List<ASTNode<ASTNode>> refs = references.get(id);
        if (refs == null) {
            return new ArrayList<>();
        }
        return refs;
    }

    public List<ASTNode<ASTNode>> getReferences(String name) {
        return getReferences(generator.idFor(name));
    }

    public Program getProgram() {
        return program;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public Node getNode(Integer id) {
        return nodes.get(id);
    }

    public Node getNode(String name) {
        Integer id = nameIndex.get(name);
        if (id == null) {
            return null;
        }
        return nodes.get(id);
    }

    public List<Node> queryNodesFrom(Integer id, Edge.Type type) {
        return fromIndex.getOrDefault(id, new HashSet<>()).stream()
                .filter((e) -> e.getType() == type)
                .map((e) -> nodes.get(e.getTarget()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Node> queryNodesTo(Integer id, Edge.Type type) {
        return toIndex.getOrDefault(id, new HashSet<>()).stream()
                .filter((e) -> type == null || e.getType() == type)
                .map((e) -> nodes.get(e.getSource()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Node> queryNodesTo(Integer id) {
        return queryNodesTo(id, null);
    }

    public List<Edge> queryEdgesTo(Integer id, Edge.Type type) {
        return toIndex.getOrDefault(id, new HashSet<>()).stream()
                .filter((e) -> type == null || e.getType() == type)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void renameNode(Integer id, String newFullName) {
        Node n = getNode(id);
        String oldName = n.getFullName();
        n.setFullName(newFullName);
        nameIndex.remove(oldName);
        nameIndex.put(newFullName, id);

        for (Node child: queryNodesFrom(id, Edge.Type.Contains)) {
            if (child.getFullName().contains(oldName)) {
                renameNode(child.getId(), child.getFullName().replace(oldName, newFullName));
            }
        }
    }
}
