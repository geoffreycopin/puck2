package graph.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.sun.org.apache.xpath.internal.operations.Equals;
import graph.Edge;
import graph.Node;
import graph.readers.ClassReader;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestProgramReader {
    private static ProgramLoader loader;

    @BeforeClass
    public static void init() {
        loader = new ProgramLoader();
        loader.addFile("testfiles/Test.java");
    }

    @Test
    public void extractClasses() {
        Set<String> expectedClasses = new HashSet<>(Arrays.asList(
                "test.SuperTest", "test.Test"));
        containsNodes(expectedClasses, Node.Type.Class);
    }

    @Test
    public void extractPackage() {
        Set<String> expectedPackages = new HashSet<>(Arrays.asList(
                "test"
        ));
        containsNodes(expectedPackages, Node.Type.Package);
    }

    @Test
    public void extractInterfaces() {
        Set<String> expectedInterfaces = new HashSet<>(Arrays.asList(
                "test.Foo"
        ));
        containsNodes(expectedInterfaces, Node.Type.Interface);
    }

    @Test
    public void containsDependency() {
        Set<TestEdge> contains = new HashSet<>(Arrays.asList(
                new TestEdge("test", "test.SuperTest"),
                new TestEdge("test", "test.Test"),
                new TestEdge("test", "test.Foo"),
                new TestEdge("test.SuperTest", "test.SuperTest.r"),
                new TestEdge("test.SuperTest", "test.SuperTest.superMethod(test.Test, test.Foo)"),
                new TestEdge("test.Test", "test.Test.r"),
                new TestEdge("test.Test", "test.Test.f"),
                new TestEdge("test.Test", "test.Test.f()"),
                new TestEdge("test.Test", "test.Test.m(int)"),
                new TestEdge("test.Test", "test.Test.m(double)"),
                new TestEdge("test.Test", "test.Test.m(test.Foo)"),
                new TestEdge("test.Foo", "test.Foo.t()"),
                new TestEdge("test.Foo", "test.Foo.t(test.Test)")
        ));
        containsEdge(contains, Edge.Type.Contains);
    }

    @Test
    public void isADependency() {
        Set<TestEdge> isA = new HashSet<>(Arrays.asList(
                new TestEdge("test.SuperTest", "test.Test"),
                new TestEdge("test.Test", "test.Foo"),
                new TestEdge("test.Test.f", "test.SuperTest")
        ));
        containsEdge(isA, Edge.Type.IsA);
    }

    @Test
    public void usesDependecy() {
        Set<TestEdge> uses = new HashSet<>(Arrays.asList(
                new TestEdge("test.SuperTest.superMethod(test.Test, test.Foo)", "test.Test"),
                new TestEdge("test.SuperTest.superMethod(test.Test, test.Foo)", "test.Foo"),
                new TestEdge("test.Test.m(test.Foo)", "test.Foo"),
                new TestEdge("test.Foo.t(test.Test)", "test.Test")
        ));
        containsEdge(uses, Edge.Type.Uses);
    }

    private void containsNodes(Set<String> expected, Node.Type type) {
        Map<String, Node> nodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        ProgramReader r = new ProgramReader(loader.getProgram());
        r.readInto(nodes, edges);

        Set<String> actual = nodes.values().stream()
                .filter(n -> n.getType() == type)
                .map(Node::getFullName)
                .collect(Collectors.toSet());

        assertEquals(actual, expected);
    }

    private void containsEdge(Set<TestEdge> expected, Edge.Type type) {
        Map<String, Node> nodes_map = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        ProgramReader r = new ProgramReader(loader.getProgram());
        r.readInto(nodes_map, edges);
        List<Node> sorted_nodes = nodes_map.values().stream()
                .sorted(Comparator.comparingInt(Node::getId))
                .collect(Collectors.toList());

        Set<String> actual = edges.stream()
                .filter(n -> n.getType() == type)
                .map(n -> new TestEdge(sorted_nodes.get(n.getSourceId()).getFullName(),
                                       sorted_nodes.get(n.getTargetId()).getFullName()).toString())
                .collect(Collectors.toSet());
        Set<String> expected_edges = expected.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());

        assertEquals(actual, expected_edges);
    }


    class TestEdge{
        public String source;
        public String target;

        public TestEdge(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String toString() {
            return String.format("<source=%s target=%s>", source, target);
        }
    }
}
