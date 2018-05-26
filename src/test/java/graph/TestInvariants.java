package graph;

import app.Puck2Runner;
import org.extendj.ast.MethodDecl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestInvariants {
    private static Graph graph;

    @BeforeClass
    public static void init() throws IOException {
        Puck2Runner runner = new Puck2Runner("src/main");
        runner.run();
        graph = runner.getGraph();
    }

    @Test
    public void packages() {
        nodesOfType(Node.Type.Package)
                .forEach((p) -> {
                    match0From(p, Edge.Type.IsA);
                    match0From(p, Edge.Type.Uses);
                    matchNFrom(p, Edge.Type.Contains,
                            nodeTypeSet(Node.Type.Class, Node.Type.Interface, Node.Type.Package));
                    match0To(p, Edge.Type.IsA);
                    match0To(p, Edge.Type.Uses);
                    matchNTo(p, Edge.Type.Contains, nodeTypeSet(Node.Type.Package));
                });
    }

    @Test
    public void classes() {
        nodesOfType(Node.Type.Class)
                .forEach((c) -> {
                    matchNFrom(c, Edge.Type.IsA, nodeTypeSet(Node.Type.Class, Node.Type.Interface));
                    matchNFrom(c, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface
                    ));
                    matchNFrom(c, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface, Node.Type.Attribute, Node.Type.Method
                    ));
                    matchNTo(c, Edge.Type.IsA, nodeTypeSet(Node.Type.Class, Node.Type.Interface));
                    matchNTo(c, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Attribute, Node.Type.MethodBody, Node.Type.Parameter, Node.Type.Method,
                            Node.Type.Class
                    ));
                    matchOptionnalTo(c, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Package, Node.Type.Class, Node.Type.Interface
                    ));
                });
    }

    @Test
    public void interfaces() {
        nodesOfType(Node.Type.Interface)
                .forEach((i) -> {
                    matchNFrom(i, Edge.Type.IsA, nodeTypeSet(Node.Type.Interface));
                    matchNFrom(i, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface
                    ));
                    matchNFrom(i, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface, Node.Type.Method
                    ));
                    matchNTo(i, Edge.Type.IsA, nodeTypeSet(Node.Type.Class, Node.Type.Interface));
                    matchNTo(i, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Attribute, Node.Type.MethodBody, Node.Type.Parameter, Node.Type.Method,
                            Node.Type.Class
                    ));
                    matchOptionnalTo(i, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Package, Node.Type.Class, Node.Type.Interface
                    ));
                });
    }

    @Test
    public void attributes() {
        nodesOfType(Node.Type.Attribute)
                .forEach((a) -> {
                    match0From(a, Edge.Type.IsA);
                    matchNFrom(a, Edge.Type.Uses, nodeTypeSet(Node.Type.Class, Node.Type.Interface));
                    match0From(a, Edge.Type.Contains);
                    match0To(a, Edge.Type.IsA);
                    matchNTo(a, Edge.Type.Uses, nodeTypeSet(Node.Type.MethodBody));
                    match1To(a, Edge.Type.Contains, nodeTypeSet(Node.Type.Class));
                });
    }

    @Test
    public void methods() {
        nodesOfType(Node.Type.Attribute)
                .forEach((m) -> {
                    match0From(m, Edge.Type.IsA);
                    matchNFrom(m, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface
                    ));
                    matchNFrom(m, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Parameter, Node.Type.MethodBody
                    ));
                    match0To(m, Edge.Type.IsA);
                    matchNTo(m, Edge.Type.Uses, nodeTypeSet(Node.Type.MethodBody));
                    match1To(m, Edge.Type.Contains, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface
                    ));
                });
    }

    @Test
    public void methodBodies() {
        nodesOfType(Node.Type.MethodBody)
                .forEach((m) -> {
                    match0From(m, Edge.Type.IsA);
                    matchNFrom(m, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface, Node.Type.Attribute,
                            Node.Type.Parameter, Node.Type.Method
                    ));
                    match0From(m, Edge.Type.Contains);
                    match0To(m, Edge.Type.IsA);
                    match0To(m, Edge.Type.Uses);
                    match1To(m, Edge.Type.Contains, nodeTypeSet(Node.Type.Method));
                });
    }

    @Test
    public void parameters() {
        nodesOfType(Node.Type.Parameter)
                .forEach((p) -> {
                    match0From(p, Edge.Type.IsA);
                    matchNFrom(p, Edge.Type.Uses, nodeTypeSet(
                            Node.Type.Class, Node.Type.Interface
                    ));
                    match0From(p, Edge.Type.Contains);
                    match0To(p, Edge.Type.IsA);
                    matchNTo(p, Edge.Type.Uses, nodeTypeSet(Node.Type.MethodBody));
                    match1To(p, Edge.Type.Contains, nodeTypeSet(Node.Type.Method));
                });
    }

    private List<Node> nodesOfType(Node.Type t) {
        return graph.getNodes().values().stream()
                .filter((n) -> n.getType() == t)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Set<Node.Type> nodeTypeSet(Node.Type... types) {
        return new HashSet<>(Arrays.asList(types));
    }

    private void match0From(Node source, Edge.Type t) {
        assertEquals(0, graph.queryNodesFrom(source.getId(), t).size());
    }

    private void matchOptionnalFrom(Node source, Edge.Type t, Set<Node.Type> targetType) {
        List<Node> d = graph.queryNodesFrom(source.getId(), t);
        if (d.size() > 1) {
            System.out.println(source);
            System.out.println(d);
            System.out.println(t);
            System.out.println(targetType);
        }
        assertTrue(d.size() <= 1);
        if (d.size() == 1) {
            assertTrue(targetType.contains(d.get(0).getType()));
        }
    }

    private void matchNFrom(Node source, Edge.Type t, Set<Node.Type> targetType) {
        List<Node> d = graph.queryNodesFrom(source.getId(), t);
        if (d.size() > 0) {
            for (Node n: d) {
                assertTrue(targetType.contains(n.getType()));
            }
        }
    }

    private void match0To(Node target, Edge.Type t) {
        assertEquals(0, graph.queryNodesTo(target.getId(), t).size());
    }

    private void match1To(Node target, Edge.Type t, Set<Node.Type> sourceType) {
        List<Node> d = graph.queryNodesTo(target.getId(), t);
        if (d.size() != 1) {
            System.out.println(target);
            System.out.println(d);
            System.out.println(graph.queryEdgesTo(target.getId(), t));
            System.out.println(t);
            System.out.println(sourceType);
        }
        assertEquals(1, d.size());
        assertTrue(sourceType.contains(d.get(0).getType()));
    }

    private void matchOptionnalTo(Node target, Edge.Type t, Set<Node.Type> sourceType) {
        List<Node> d = graph.queryNodesTo(target.getId(), t);
        assertTrue(d.size() <= 1);
        if (d.size() == 1) {
            assertTrue(sourceType.contains(d.get(0).getType()));
        }
    }

    private void matchNTo(Node target, Edge.Type t, Set<Node.Type> sourceType) {
        List<Node> d = graph.queryNodesTo(target.getId(), t);
        if (d.size() > 0) {
            for (Node n: d) {
                if (! sourceType.contains(n.getType())) {
                    System.out.println(target);
                    System.out.println(n);
                    System.out.println(t);
                    System.out.println(sourceType);
                }
                assertTrue(sourceType.contains(n.getType()));
            }
        }
    }
}
